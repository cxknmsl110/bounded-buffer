from Ui_second import *
import sys
from PyQt5.QtWidgets import QMainWindow,QApplication
class Buffer:
    def __init__(self):
        self.empty=3
        self.full=0
        self.buffer=[]
        self.block_producer = []
        self.block_consumer = []
    def put(self,data):
        self.buffer.append(data)
    def get(self):
        data=self.buffer.pop(0)
        return data
    def Pempty(self):
        self.empty-=1
        if self.empty<0:
            return False
        else:
            return True
    def Pfull(self):
        self.full-=1
        if self.full<0:
            return False
        else:
            return True
    def Vempty(self):
        self.empty+=1
        if self.empty<1:
            self.block_producer[0].wakeup()
            self.block_producer.pop(0)
    def Vfull(self):
        self.full += 1
        if self.full < 1:
            self.block_consumer[0].wakeup()
            self.block_consumer.pop(0)
class Producer:
    def __init__(self,button,buffer):
        self.button=button
        self.buffer=buffer
    def wakeup(self):
        self.button.setEnabled(True)
        self.dowork()
    def block(self):
        self.button.setEnabled(False)
        self.buffer.block_producer.append(self)
    def main(self):
        self.data=self.button.text()+'\n的数据'
        if self.buffer.Pempty():
            self.dowork()
        else:
            self.block()
    def dowork(self):
        self.buffer.put(self.data)
        self.buffer.Vfull()
class Consumer:
    def __init__(self,button,buffer):
        self.button=button
        self.buffer=buffer
    def wakeup(self):
        self.button.setEnabled(True)
        self.dowork()
    def block(self):
        self.button.setEnabled(False)
        self.buffer.block_consumer.append(self)
    def main(self):
        if self.buffer.Pfull():
            self.dowork()
        else:
            self.block()
    def dowork(self):
        self.buffer.get()
        self.buffer.Vempty()
class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)
        self.setupUi(self)
        self.Buffer=Buffer()
        self.Producer={self.pushButton:Producer(self.pushButton,self.Buffer),
                       self.pushButton_2:Producer(self.pushButton_2,self.Buffer),
                       self.pushButton_3:Producer(self.pushButton_3,self.Buffer),
                       self.pushButton_4:Producer(self.pushButton_4,self.Buffer),
                       self.pushButton_5:Producer(self.pushButton_5,self.Buffer)}
        self.Consumer={self.pushButton_6:Consumer(self.pushButton_6, self.Buffer),
                       self.pushButton_7:Consumer(self.pushButton_7, self.Buffer),
                       self.pushButton_8:Consumer(self.pushButton_8, self.Buffer),
                       self.pushButton_9:Consumer(self.pushButton_9, self.Buffer),
                       self.pushButton_10:Consumer(self.pushButton_10, self.Buffer)}
        self.buffer_obj=[self.label,self.label_2,self.label_3]
    def pro(self):
        Producer=self.Producer[self.sender()]
        Producer.main()
        self.brush()
    def con(self):
        Consumer = self.Consumer[self.sender()]
        Consumer.main()
        self.brush()
    def brush(self):
        for i in range(3):
            try:
                self.buffer_obj[i].setText(self.Buffer.buffer[i])
            except:
                self.buffer_obj[i].setText('缓冲区')
        text = '生产者阻塞队列'
        if len(self.Buffer.block_producer) > 0:
            text = ''
            for i in self.Buffer.block_producer:
                text += i.button.text() + '  '
        self.label_4.setText(text)
        print('p:%d' % len(self.Buffer.block_producer))
        print('c:%d' % len(self.Buffer.block_consumer))
        print(self.Buffer.buffer)
        print('empty:%d,full:%d' % (self.Buffer.empty, self.Buffer.full))
        text = '消费者阻塞队列'
        if len(self.Buffer.block_consumer) > 0:
            text = ''
            for i in self.Buffer.block_consumer:
                text += i.button.text() + '  '
        self.label_5.setText(text)
if __name__=='__main__':
    MainApp = QApplication(sys.argv)
    MainWin = MainWindow()
    MainWin.show()
    sys.exit(MainApp.exec_())