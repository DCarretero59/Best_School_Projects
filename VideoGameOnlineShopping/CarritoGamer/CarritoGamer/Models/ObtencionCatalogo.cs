using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Web;

namespace CarritoGamer.Models
{
    public class ObtencionCatalogo
    {
        
        public int characterID { get; set; }
        public string ID { get; set; }
        public string Name { get; set; }
        public Image Photo { get; set; }

        public byte[] bytes { get; set; }
        private bool connection_open;
        private MySqlConnection connection;

        public ObtencionCatalogo()
        {

        }

        public ObtencionCatalogo(int arg_id)
        {
            Get_Connection();
            characterID = arg_id;
            try
            {
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText =
            string.Format("select idCharacter, characterName, "+
                "Image from characters where idCharacter = 1", characterID);

                MySqlDataReader reader = cmd.ExecuteReader();

                try
                {
                    reader.Read();

                    if (reader.IsDBNull(0) == false)
                        ID = reader.GetString(0);
                    else
                        ID = null;

                    if (reader.IsDBNull(1) == false)
                        Name = reader.GetString(1);
                    else
                        Name = null;

                    if (reader.IsDBNull(2) == false){
                        Photo = byteArrayToImage(reader["Image"] as byte[]);
                        bytes = imageToByteArray(Photo);
                    }
                    else
                    {
                        Photo = null;
                    }
                    reader.Close();

                }
                catch (MySqlException e)
                {
                    string MessageString = "Read error occurred  "+
                        "/ entry not found loading the Column details: "
                        + e.ErrorCode + " - " + e.Message + "; \n\nPlease Continue";
                    reader.Close();
                    ID = MessageString;
                    Name = Name = null;
                }
            }
            catch (MySqlException e)
            {
                string MessageString = "The following error occurred loading the Column details: "
                    + e.ErrorCode + " - " + e.Message;
                ID = MessageString;
                Name  = null;
            }
            connection.Close();


        }

        private void Get_Connection()
        {
            connection_open = false;

            connection = new MySqlConnection();
            connection.ConnectionString = 
                ConfigurationManager.ConnectionStrings["MySQLConnection"].ConnectionString;

            if (Open_Local_Connection())
            {
                connection_open = true;
            }
            

        }

        private bool Open_Local_Connection()
        {
            try
            {
                connection.Open();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }

        private Image byteArrayToImage(byte[] byteArrayIn)
        {
            MemoryStream ms = new MemoryStream(byteArrayIn);
            ms.Position = 0;
            Image returnImage = Image.FromStream(ms);
            return returnImage;
        }

        private byte[] imageToByteArray(Image imageIn)
        {
            MemoryStream ms = new MemoryStream();
            imageIn.Save(ms, System.Drawing.Imaging.ImageFormat.Gif);
            return ms.ToArray();
        }
    }
}