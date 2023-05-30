package com.example.myamazon;

public interface DataTransferListener {
   void onDataTransfer(String data);
   void onDataTransfer(int data);
   void onDataTransfer(double data);
   void onDataTransfer(char data);
}
