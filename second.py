import random
import time
import threading
from Ui_second import *
import sys
from PyQt5.QtWidgets import QMainWindow,QApplication
from PyQt5.Qt import Qt
from PyQt5.QtGui import QPixmap,QPainter,QPen
class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)
        self.setupUi(self)
        self.buffer = []
        self.block_producer = []
        self.block_consumer = []
        self.buffer_obj=[self.label,self.label_2,self.label_3]
    def pro(self):
        Producer=self.sender()
        data=Producer.text()+'\n的数据'
        if self.Pempty():
            self.put(data)
            self.Vfull()
        else:
            self.block_producer.append(Producer)
            self.block(Producer)
        self.brush()
    def con(self):
        Consumer = self.sender()
        if self.Pfull():
            self.get()
            self.Vempty()
        else:
            self.block_consumer.append(Consumer)
            self.block(Consumer)
        self.brush()
    def put(self,data):
        self.buffer.append(data)
        for i in range(3):
            try:
                self.buffer_obj[i].setText(self.buffer[i])
            except:
                self.buffer_obj[i].setText('缓冲区')
    def get(self):
        data=self.buffer.pop(0)
        for i in range(3):
            try:
                self.buffer_obj[i].setText(self.buffer[i])
            except:
                self.buffer_obj[i].setText('缓冲区')
        return data
    def brush(self):
        text = '生产者阻塞队列'
        if len(self.block_producer) > 0:
            text = ''
            for i in self.block_producer:
                text += i.text() + '  '
        self.label_4.setText(text)
        text = '消费者阻塞队列'
        if len(self.block_consumer) > 0:
            text = ''
            for i in self.block_consumer:
                text += i.text() + '  '
        self.label_5.setText(text)
    def block(self,obj):
        obj.setEnabled(False)
    def wakeup(self,obj):
        obj.setEnabled(True)
    def Pempty(self):
        if len(self.buffer)<3:
            return True
        else:
            return False
    def Pfull(self):
        if len(self.buffer)>0:
            return True
        else:
            return False
    def Vempty(self):
        if len(self.block_producer)>0:
            self.wakeup(self.block_producer[0])
            data=self.block_producer[0].text()+'\n的数据'
            self.put(data)
            self.block_producer.pop(0)
    def Vfull(self):
        if len(self.block_consumer) > 0:
            self.wakeup(self.block_consumer[0])
            self.get()
            self.block_consumer.pop(0)
if __name__=='__main__':
    MainApp = QApplication(sys.argv)
    MainWin = MainWindow()
    MainWin.show()
    sys.exit(MainApp.exec_())