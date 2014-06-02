% This is written by Zhiyang Ong to determine the time order
% complexity of the sorting algorithms

x=[1:5:100000]; % 0000];
y1=x;
% subplot(2,2,1),plot(y1,'b-.'), xlabel('n'),ylabel('O()')
y2=x.^2;
% y3=x+x;
% y4=x.*log(x);
y3=x.*log(x);
y4=x.*sqrt(x);
% plot(x,y1,'bv',x,y2,'r--',x,y3,'k-',x,y4,'gx')
plot(x,y1,'bv',x,y3,'k-',x,y4,'gx')
title('Time order complexity of sorting algorithms')