#include <iostream>
#include <stdlib.h>
#include <string>
#include <math.h>
#include <chrono>
#include <time.h>
#include <fstream>
using namespace std;

// void MainMenu(){
//   while(1){
//     int opt;
//     cout << endl << "----------------------------" << endl;
//     cout << endl << "      Main Menu   " << endl;
//     cout << endl << "----------------------------" << endl;
//     cout << endl << "1 - Generate Keys" << endl;
//     cout << endl << "2 - Encrypt Message" << endl;
//     cout << endl << "3 - Decrypt Message" << endl;
//     cout << endl << "4 - Brute Force" << endl;
//     cout << endl << "----------------------------" << endl;
//     cout << endl << "      0 to Exit   " << endl;
//     cin >> opt;
//     cout << "\n" << endl;

//     switch (opt){
//         case 1://Generate Keys
//             int typeKey;
//             cout << endl << "----------------------------" << endl;
//             cout << endl << "      Generate Key   " << endl;
//             cout << endl << "----------------------------" << endl;
//             cout << endl << "1 - Public Key" << endl;
//             cout << endl << "2 - Private Key" << endl;
//             cout << endl << "----------------------------" << endl;
//             cout << endl << "      0 to Exit   " << endl;
//             cin >> typeKey;
//             cout << "\n" << endl;
//             break;
//         case 2:{
//             string msg;
//             double n, e;
//             cout << endl << "What is the Message?" << endl;
//             cin >> mes;
//             cout << endl << "What is the value of N?" << endl;               
//             cin >> n;
//             cout << endl << "What is the value of E?" << endl;
//             cin >> e;
//             ofstream Encrypted("Encrypted.txt");
//             if (Encrypted.is_open()){
//                auto begin = chrono::high_resolution_clock::now(); 
//                for(i=0;i<msg.lenght();i++){
//                   msgInt = msgInt + 
//                }
//                auto end = chrono::high_resolution_clock::now();    
//                auto dur = end - begin;
//                Encrypted << msg;
//                Encrypted.close();
//                auto ms = std::chrono::duration_cast<std::chrono::milliseconds>(dur).count();
//                cout << ms <<" ms" << endl;
//             }
//             else{
//                cout << "Unable to open file" << endl;
//             }

//          }
//             break;
//          case 4:

//             break;
//         case 0:
//             exit(1);
//         default:
//             break;
//     }
//  }
// }

// Greatest Common Divisor
int gcd(int a, int b) {
   int t;
   while(1) {
      t = a%b;
      if(t==0)
         return b;
      a = b;
      b = t;
   }
}

int main() {
   //MainMenu();
   double p = 13;
   double q = 11;
   double n=p*q;//calculate n
   double track;
   double phi= (p-1)*(q-1);//calculate phi
   //public key
   //e stands for encrypt
   double e=7;
   //for checking that 1 < e < phi(n) and gcd(e, phi(n)) = 1; i.e., e and phi(n) are coprime.
   while(e < phi){
      track = gcd(e,phi);
      if(track==1)
         break;
      else
         e++;
   }
   //private key
   //d stands for decrypt
   //choosing d such that it satisfies d*e = 1 mod phi
   double d1=1/e;
   double d=fmod(d1,phi);
   double message = 3;
   //message = atof(message.c_str());
   double c = pow(message,e); //encrypt the message
   double m = pow(c,d);
   c=fmod(c,n);
   m=fmod(m,n);
   cout<<"Original Message = "<<message;
   cout<<"\n"<<"p = "<<p;
   cout<<"\n"<<"q = "<<q;
   cout<<"\n"<<"n = pq = "<<n;
   cout<<"\n"<<"phi = "<<phi;
   cout<<"\n"<<"e = "<<e;
   cout<<"\n"<<"d = "<<d;
   cout<<"\n"<<"Encrypted message = "<<c;
   cout<<"\n"<<"Decrypted message = "<<m;
   return 0;
}