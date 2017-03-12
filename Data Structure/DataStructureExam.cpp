#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <string.h>

struct persona
{
 char nombre[50];
 char genero[25];
 int edad;
};

struct nodo
{
 struct persona dato;
 struct nodo *proximo;
 struct nodo *anterior;
};

struct nodo *nuevonodo();
void mostrar(struct nodo *lista);
struct nodo *crealista(struct nodo *lista, struct persona x);
void eliminarID(int id);
void insertarIDantes(int id);
void insertarIDdespues(int id);
struct nodo *insertaPersona(struct nodo *);


struct nodo *pos, *lista=NULL;

int main(void)
{
     int opc, id;
               do{
                        system("cls");
                        printf("Deme una opcion: \n1. Mostrar\n2. Agregar(Antes)\n3. Agregar(Despues)\n4. Borrar\n5. Salir\n\n");
                        scanf("%d", &opc);
                        switch(opc){
                                    case 1:
                                         mostrar(lista);
                                         break;
                                    case 2:
                                         printf("En que posicion desea agregar a la persona (antes)?");
                                         scanf("%d", &id);
                                         insertarIDantes(id);
                                         break;
                                    case 3:
                                         printf("En que posicion desea agregar a la persona (despues)?");
                                         scanf("%d", &id);
                                         insertarIDdespues(id);
                                         break;
                                    case 4:
                                         printf("Que posicion de persona desea retirar?");
                                         scanf("%d", &id);
                                         eliminarID(id);
                                         break;
                                         }
                        }while(opc!=5);
}

struct nodo *nuevonodo(void){
       struct nodo *p;
       p=(struct nodo *)malloc(sizeof(struct nodo));
       if(p==NULL)
       {
          printf("Memoria RAM Llena");
          getch();
          exit(0);
          }
       return p;
}
struct nodo *crealista(struct nodo *lista, struct persona x)
{
       struct nodo *p;
       p=nuevonodo();
       (*p).dato=x;
       (*p).proximo=NULL;
       (*p).anterior=lista;
       if(lista!=NULL){
                      (*lista).proximo=p; // Si hay nodo anterior en prox pongo la direccion del nodo actual
                      
                      }
           return p;
       }
       
void mostrar(struct nodo *lista)
{
     pos=lista;
     int i=0;
     while(pos!=NULL)
     {
          i++;
          printf("Pos: %d - Nombre: %s - Edad: %d - Genero: %s \n Ant: %p - Actual: %p - Desp:%p \n",i,pos->dato.nombre,pos->dato.edad, pos->dato.genero,(*pos).anterior,pos,(*pos).proximo);
          pos=(*pos).proximo;
     }
   getch();
}

void eliminarID(int id){
     struct nodo *aux, *aux2;
     pos=lista;
     int i=0;
     while(pos!=NULL)
     {
          i++;
          if(id==i){
                               if((*pos).proximo==NULL){
                                     aux=pos;
                                     pos=(*pos).anterior;
                                     (*pos).proximo=NULL;
                                     free(aux);
                                     break;
                                    }
                               if((*pos).proximo!=NULL && (*pos).anterior!=NULL){
                                       aux=pos;
                                       pos=(*pos).anterior;
                                       (*pos).proximo=(*aux).proximo;
                                       aux2=(*pos).proximo;
                                       (*aux2).anterior=(*aux).anterior;
                                       free(aux);     
                                       break;
                                       }
                              if((*pos).anterior==NULL){
                                     aux=pos;
                                     pos=(*pos).proximo;
                                     (*pos).anterior=NULL;
                                     free(aux);
                                     lista=pos;
                                     break;
                                                        }  
 
               }
          pos=(*pos).proximo;
     }
     }
     
void insertarIDantes(int id){
     pos=lista;
     struct nodo *aux, *aux2,  *nuevo;
     struct persona x;
     bool flag=false;
     if(lista==NULL){
                  printf("No data has been added yet, adding new registry\n\n");
                  lista=insertaPersona(lista);
     }else{
     int i=0;
     while(pos!=NULL)
     {
          i++;
          if(id==i){
                               flag=false;
                              if((*pos).anterior==NULL){
                                     struct persona x;
                                     fflush(stdin);
                                     printf("Ingrese nombre: ");
                                     gets(x.nombre);
                                     printf("Ingrese genero: ");
                                     gets(x.genero);
                                     fflush(stdin);
                                     printf("Ingrese edad: ");
                                     scanf("%d", &x.edad);
                                     nuevo=nuevonodo();
                                     (*nuevo).dato=x;
                                     (*nuevo).proximo=pos;
                                     (*nuevo).anterior=NULL;
                                     (*pos).anterior=nuevo;
                                     lista=nuevo;
                                     break;
                                                        }
                              else{
                                   aux=(*pos).anterior;
                                     fflush(stdin);
                                     printf("Ingrese nombre: ");
                                     gets(x.nombre);
                                     printf("Ingrese genero: ");
                                     gets(x.genero);
                                     fflush(stdin);
                                     printf("Ingrese edad: ");
                                     scanf("%d", &x.edad);
                                     nuevo=nuevonodo();
                                     (*nuevo).dato=x;
                                     (*nuevo).proximo=pos;
                                     (*nuevo).anterior=(*pos).anterior;
                                     (*pos).anterior=nuevo;
                                     (*aux).proximo=nuevo;
                                     break;
                                   }

                                                                  
               }else{
                     flag=true;
                     }
          pos=(*pos).proximo;
     }
     }
     if(flag){
              
                     printf("Position not found");
                     getch();
                     }
     }
     
void insertarIDdespues(int id){
     pos=lista;
     struct nodo *aux, *aux2, *nuevo;
     struct persona x;
     bool flag=false;
     if(lista==NULL){
                  printf("No data has been added yet, adding new registry\n\n");
                  lista=insertaPersona(lista);
     }else{
     int i=0;
     while(pos!=NULL)
     {
          i++;
          if(id==i){
                              if((*pos).proximo==NULL){
                                     flag=false;
                                     struct persona x;
                                     fflush(stdin);
                                     printf("Ingrese nombre: ");
                                     gets(x.nombre);
                                     printf("Ingrese genero: ");
                                     gets(x.genero);
                                     fflush(stdin);
                                     printf("Ingrese edad: ");
                                     scanf("%d", &x.edad);
                                     nuevo=nuevonodo();
                                     (*nuevo).dato=x;
                                     (*nuevo).proximo=NULL;
                                     (*nuevo).anterior=pos;
                                     (*pos).proximo=nuevo;
                                     break;
                                                        }
                              else{
                                     aux=(*pos).proximo;
                                     fflush(stdin);
                                     printf("Ingrese nombre: ");
                                     gets(x.nombre);
                                     printf("Ingrese genero: ");
                                     gets(x.genero);
                                     fflush(stdin);
                                     printf("Ingrese edad: ");
                                     scanf("%d", &x.edad);
                                     nuevo=nuevonodo();
                                     (*nuevo).dato=x;
                                     (*nuevo).proximo=(*aux).proximo;
                                     (*nuevo).anterior=pos;
                                     (*pos).proximo=nuevo;
                                     (*aux).anterior=nuevo;
                                     break;
                                   }
                   
               }else{
                     flag=true;
                     }
          pos=(*pos).proximo;
     }
     }
     if(flag){
              
                     printf("Position not found");
                     getch();
                     }
     }
     
     struct nodo *insertaPersona(struct nodo *pos)
{
     
     struct persona x;
     int opc;
     fflush(stdin);
     printf("Ingrese nombre: ");
     gets(x.nombre);
     printf("Ingrese genero: ");
     gets(x.genero);
     fflush(stdin);
     printf("Ingrese edad: ");
     scanf("%d", &x.edad);
     pos=crealista(pos,x);

     getch();
     return pos;   
       }

