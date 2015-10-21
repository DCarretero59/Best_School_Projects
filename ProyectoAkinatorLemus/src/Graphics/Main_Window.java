/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import ConexionBase.DataBase;
import Objects.Answers;
import Objects.Questions;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Usuario
 */
public class Main_Window extends javax.swing.JFrame {

    private ArrayList<Questions> questions = new ArrayList<>();
    private ArrayList<Answers> answers = new ArrayList<>();
    private int position = 0;
    private int questionPos = 1;

    /**
     * Creates new form Main_Window
     */
    public Main_Window() {
        initComponents();
        try {
            getQuestions();
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "MySQL Error: " + ex.getMessage());
            System.exit(0);
        }
        questionsArea.setText(questions.get(0).getQuestion());
        questionLbl.setText("Question N : " + questionPos);
    }

    public final void getQuestions() throws SQLException {
        DataBase db = new DataBase();
        ResultSet rs = db.getRecords("SELECT q.idquestions, g.Categories, q.Question FROM questions q "
                + "INNER JOIN categories g ON q.idCategory=g.idcategories "
                + "WHERE q.Active=1 ORDER BY g.Categories");
        while (rs.next()) {
            questions.add(new Questions(
                    rs.getInt(1), rs.getString(2), rs.getString(3)));
            answers.add(new Answers(rs.getInt(1), rs.getString(2)));
        }
        db.closeConnection();
    }

    public void inferProcess(double answer) {
        answers.get(position).setAnswer(answer);
        questionLbl.setText("Question N : " + questionPos++);

        if (position + 1 == questions.size()) {
            try {
                KNN();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "MySQL Error: " + ex.getMessage());
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Image Error: " + ex.getMessage());
            }

        } else {
            if (answer == 1) {
                String category = answers.get(position).getCategory();
                position++;
                fillAnsweredCategory(category);
                questionsArea.setText(questions.get(position).getQuestion());
            } else {
                position++;
                questionsArea.setText(questions.get(position).getQuestion());
            }
        }
    }

    public void fillAnsweredCategory(String category) {
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(position).getCategory().equals(category)) {
                answers.get(position).setAnswer(0);
                position++;
            }
        }
    }

    public void KNN() throws SQLException, IOException {
        DataBase db = new DataBase();
        ResultSet rs1 = db.getRecords("Select * From characters WHERE Active=1");
        double results = 0;
        int realAnswerId = 0;
        while (rs1.next()) {
            double sum = 0;
            for (Answers a : answers) {
                ResultSet rs2 = db.getRecords("Select Answer From answers WHERE idCharacter=" + rs1.getInt(1) + " AND idQuestion=" + a.getIdQuestion());
                while (rs2.next()) {
                    System.out.println(a.getAnswer() + " - " + rs2.getDouble(1));
                    sum = sum + Math.exp(a.getAnswer() - rs2.getDouble(1));
                }
            }

            double operation = Math.sqrt(sum);
            if (operation < results) {
                results = operation;
                realAnswerId = rs1.getInt(1);
            } else if (results == 0) {
                results = operation;
                realAnswerId = rs1.getInt(1);
            }
            System.out.println("");
            System.out.println("");
        }

        int option = javax.swing.JOptionPane.showConfirmDialog(null,
                "Is your character " + db.getValue("SELECT characterName from characters where idCharacter = " + realAnswerId), "Is the answer correct?",
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE, getIcon(realAnswerId, db));
        if (option == javax.swing.JOptionPane.YES_OPTION) {
            playAgain(db);
        } else if (option == javax.swing.JOptionPane.YES_NO_CANCEL_OPTION) {
            String character = javax.swing.JOptionPane.showInputDialog("Help us improve by adding your character: ");
            if (character.equals("")) {
                javax.swing.JOptionPane.showMessageDialog(null, "That's not a valid character name");
                playAgain(db);
            } else {
                if (db.executeQuery("INSERT INTO characters(characterName) VALUES ('" + character + "')")) {
                    int id = db.getValueInt("SELECT idCharacter FROM characters WHERE characterName='" + character + "'");
                    String transaction = "SELECT * FROM characters WHERE characterName='" + character + "'";
                    for (Answers a : answers) {
                        transaction = transaction + ";INSERT INTO answers VALUES (" + id + ", " + a.getIdQuestion() + ", " + a.getAnswer() + ")";
                    }
                    if (db.executeTransaction(transaction)) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Character added successfully");
                        addImage(id, db);
                        playAgain(db);
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Could not add your character");
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Could not add your character");
                }
            }

        } else if (option == javax.swing.JOptionPane.YES_NO_OPTION) {
            db.closeConnection();
            System.exit(0);
        }
        db.closeConnection();
    }

    public ImageIcon getIcon(int characterId, DataBase db) throws SQLException, IOException {
        ResultSet rs = db.getRecords("SELECT Image from characters where idCharacter = " + characterId);
        rs.next();

        ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes(1));
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpeg");

        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;

        ImageInputStream iis = ImageIO.createImageInputStream(source);

        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();

        Image image = reader.read(0, param); // this line is the problem
        return new ImageIcon(image);
    }

    public void addImage(int characterId, DataBase db) throws SQLException, FileNotFoundException {
        int option = javax.swing.JOptionPane.showConfirmDialog(null,
                "Do you want to add an image to your character?");
        if (option == javax.swing.JOptionPane.YES_OPTION) {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.OPEN_DIALOG) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                InputStream inputStream = new FileInputStream(new File(filePath));

                String sql = "UPDATE characters SET Image=? WHERE idCharacter=" + characterId;
                PreparedStatement statement = db.getConnection().prepareStatement(sql);
                statement.setBlob(1, inputStream);
                statement.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(null, "Image added");
            }
        }
    }

    public void playAgain(DataBase db) {
        int option = javax.swing.JOptionPane.showConfirmDialog(null,
                "Do you want to play again?");
        if (option == javax.swing.JOptionPane.YES_OPTION) {
            position = 0;
            questionPos = 1;
            questionLbl.setText("Question N : " + questionPos);
            questionsArea.setText(questions.get(0).getQuestion());
        } else {
            db.closeConnection();
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        questionsGroup = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        questionLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionsArea = new javax.swing.JTextArea();
        answersPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Akinator");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        questionLbl.setText("Question N ");

        questionsArea.setEditable(false);
        questionsArea.setBackground(new java.awt.Color(240, 240, 240));
        questionsArea.setColumns(20);
        questionsArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        questionsArea.setLineWrap(true);
        questionsArea.setRows(5);
        questionsArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        questionsArea.setEnabled(false);
        jScrollPane1.setViewportView(questionsArea);

        answersPanel.setLayout(null);

        jButton1.setText("Don't Know");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        answersPanel.add(jButton1);
        jButton1.setBounds(120, 0, 106, 23);

        jButton2.setText("Yes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        answersPanel.add(jButton2);
        jButton2.setBounds(230, 0, 106, 23);

        jButton3.setText("Probably not");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        answersPanel.add(jButton3);
        jButton3.setBounds(10, 30, 158, 23);

        jButton4.setText("No");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        answersPanel.add(jButton4);
        jButton4.setBounds(10, 0, 106, 23);

        jButton5.setLabel("Probably");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        answersPanel.add(jButton5);
        jButton5.setBounds(180, 30, 158, 23);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(answersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(questionLbl)
                .addGap(146, 146, 146))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(answersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        inferProcess(0.5);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        inferProcess(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        inferProcess(0.25);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        inferProcess(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        inferProcess(0.75);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel answersPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel questionLbl;
    private javax.swing.JTextArea questionsArea;
    private javax.swing.ButtonGroup questionsGroup;
    // End of variables declaration//GEN-END:variables
}
