import random
import time
import threading
from Ui_main import *
import sys
from PyQt5.QtWidgets import QMainWindow,QApplication
from PyQt5.Qt import Qt
from PyQt5.QtGui import QPixmap,QPainter,QPen
class Buffer:
    def __init__(self,size=5):
        self.size=size
        self.full=0
        self.empty=size
        self.buffer=[]
        self.block_buffer=[]
        self.block_producer=[]
        self.block_consumer = []
        self.buffer_semaphore = 1
        self.sleeptime=0.005
    def apply(self,object):
        self.buffer_semaphore-=1
        if self.buffer_semaphore<0:
            self.block_buffer.append(object)
            return False
        elif self.buffer_semaphore==0:
            return True
        else:
            return False
    def release(self):
        self.buffer_semaphore+=1
        if self.buffer_semaphore<1:
            self.block_buffer[0].wakeup()
            self.block_buffer.pop(0)
    def Pempty(self,object):
        self.empty-=1
        if self.empty<0:
            self.block_producer.append(object)
            return False
        else:
            return True
    def Pfull(self,object):
        self.full -= 1
        if self.full<0:
            self.block_consumer.append(object)
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
    def put(self,data):
        self.buffer.append(data)
        time.sleep(self.sleeptime)
    def get(self):
        data = self.buffer.pop(0)
        return data
    def refresh(self):
        while MainWin.thread_flag:
            painter = QPainter(MainWin.pix)
            MainWin.init(painter)
            painter.end()
            #print(self.buffer,self.buffer_semaphore,self.block_buffer,self.block_producer,self.block_consumer)
            MainWin.label_3.setPixmap(MainWin.pix)
            time.sleep(0.001)
    def start(self):
        producer_thread = threading.Thread(target=self.refresh)
        producer_thread.start()
class Producer:
    def __init__(self,buffer,speed=1.0):
        self.buffer=buffer
        self.speed = speed
        self.block_flag=False
        self.data=None
        self.start()
    def production(self):
        time.sleep(1 / self.speed)
        data = random.randint(0, 9)
        return data
    def block(self):
        self.block_flag=True
        while self.block_flag:
            pass
    def wakeup(self):
        self.block_flag=False
    def main(self):
        while MainWin.thread_flag:
            data=self.production()
            if self.buffer.Pempty(self)==False:
                self.block()
            if self.buffer.apply(self)==False:
                self.block()
            self.data=data
            self.buffer.put(self.data)
            self.data=None
            self.buffer.Vfull()
            self.buffer.release()
    def start(self):
        producer_thread = threading.Thread(target=self.main)
        self.flag = 1
        producer_thread.start()
class Consumer:
    def __init__(self,buffer,speed=1.0):
        self.buffer=buffer
        self.speed = speed
        self.block_flag=False
        self.data=None
        self.start()
    def deal(self):
        self.data=None
        time.sleep(1 / self.speed)
    def block(self):
        self.block_flag=True
        while self.block_flag:
            pass
    def wakeup(self):
        self.block_flag=False
    def main(self):
        while MainWin.thread_flag:
            if self.buffer.Pfull(self)==False:
                self.block()
            if self.buffer.apply(self)==False:
                self.block()
            self.data=self.buffer.get()
            time.sleep(self.buffer.sleeptime)
            self.buffer.Vempty()
            self.buffer.release()
            self.deal()
    def start(self):
        producer_thread = threading.Thread(target=self.main)
        self.flag = 1
        producer_thread.start()
class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)
        self.setupUi(self)
        self.thread_flag=True
        self.buffer=Buffer(20)
        self.P=[]
        self.C=[]
        self.flag=0
        self.color = [Qt.red,Qt.yellow,Qt.green,
                      Qt.blue,Qt.gray,Qt.darkRed,
                      Qt.darkGreen,Qt.darkBlue,Qt.magenta,Qt.darkYellow]
        self.pix = QPixmap(600, 400)
        painter = QPainter(self.pix)
        self.init(painter)
        painter.end()
        self.label_3.setPixmap(self.pix)
    def init(self,painter):
        self.pix.fill(Qt.white)
        pen = QPen(Qt.black, 2)
        painter.setPen(pen)
        painter.drawRect(220, 130, 160, 140)
        painter.drawText(250, 150,'buffer')
        painter.drawText(200, 100, ' 消费者/生产者与缓冲区连线代表其正在')
        painter.drawText(200, 120, ' 访问缓冲区,线的颜色代表不同的数据的值')

        painter.drawText(200, 300, '*注:访问缓冲区时均有%.3fs的占用时间'%self.buffer.sleeptime)
        painter.drawText(200, 320, ' 所以总速度过大时大部分进程会阻塞在缓冲区')
        for i in range(4):
            for j in range(5):
                painter.drawRect(250+j*20, 160+i*20, 20, 20)
                if len(self.buffer.buffer)>0 and len(self.buffer.buffer)>i*5+j:
                    painter.drawText(257+j*20, 175+i*20,str(self.buffer.buffer[i*5+j]))
        for i in range(len(self.P)):
            painter.drawRect(20, 20+80*i, 40, 40)
            if self.P[i].block_flag==False:
                if self.P[i].data != None:
                    painter.setPen(QPen(self.color[self.P[i].data], 4))
                    painter.drawLine(60, 40+80*i,220,200)
                painter.setPen(QPen(Qt.green, 40))
            else:
                if self.P[i] in self.buffer.block_producer:
                    painter.setPen(QPen(Qt.red, 40))
                else:
                    painter.setPen(QPen(Qt.yellow, 40))
            painter.drawPoint(40,40+80*i)
            painter.setPen(pen)
            painter.drawText(10, 75+80*i, str(self.P[i].speed)+'个/秒')
        for i in range(len(self.C)):
            painter.drawRect(540, 20+80*i, 40, 40)
            if self.C[i].block_flag==False:
                if self.C[i].data != None:
                    painter.setPen(QPen(self.color[self.C[i].data], 4))
                    painter.drawLine(540, 40 + 80 * i, 380, 200)
                painter.setPen(QPen(Qt.green, 40))
            else:
                if self.C[i] in self.buffer.block_consumer:
                    painter.setPen(QPen(Qt.red, 40))
                else:
                    painter.setPen(QPen(Qt.yellow, 40))
            painter.drawPoint(560,40+80*i)
            painter.setPen(pen)
            painter.drawText(520, 75 + 80 * i, str(self.C[i].speed) + '个/秒')
        painter.drawText(240, 380, '缓冲区阻塞队列个数:%d'%len(self.buffer.block_buffer))
        painter.drawText(80, 360, '生产者个数:%d/5' % len(self.P))
        painter.drawText(80, 380, '生产者阻塞队列个数:%d' % len(self.buffer.block_producer))
        painter.drawText(400, 360, '消费者:%d/5' % len(self.C))
        painter.drawText(400, 380, '消费者阻塞队列个数:%d' % len(self.buffer.block_consumer))
        painter.drawText(240, 20, '绿色:正常运行')
        painter.drawText(240, 40, '红色:处于生产者/消费者 阻塞队列')
        painter.drawText(240, 60, '黄色:处于缓冲区阻塞队列')
    def addP(self):
        if self.flag==0:
            self.flag = 1
            self.buffer.start()
        if len(self.P)<5:
            try:
                speed = float(self.lineEdit.text())
                temp=Producer(self.buffer,speed)
                self.P.append(temp)
            except:
                pass
        else:
            print('生产者上限')
    def addC(self):
        if self.flag == 0:
            self.flag = 1
            self.buffer.start()
        if len(self.C) < 5:
            try:
                speed=float(self.lineEdit_2.text())
                temp = Consumer(self.buffer,speed)
                self.C.append(temp)
            except:
                pass
        else:
            print('消费者上限')
    def setsleep(self):
        try:
            speed = float(self.lineEdit_3.text())
            self.buffer.sleeptime=speed
            print(speed)
        except:
            pass
    def closeEvent(self, *args, **kwargs):
        self.thread_flag=False
if __name__=='__main__':
    MainApp = QApplication(sys.argv)
    MainWin = MainWindow()
    MainWin.show()
    sys.exit(MainApp.exec_())
