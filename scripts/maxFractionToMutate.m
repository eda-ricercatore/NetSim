% This is written by Zhiyang Ong
x=[1:1:100];
%y=1-log(x);
y=0.5 * x.^(-0.17);
%y=0.5*0.2.^(x/1000);
plot(x,y)