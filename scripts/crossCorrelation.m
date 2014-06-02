% This is a MATLAB script to plot the simulation results from NetSim,
% which optimizes a generic telecommunications network
% Author: Zhiyang Ong and Andy Hao-Wei Lo

x=[-0.32 0.28 -0.5 0.48 0.6 -0.20 0.38 -0.49 -0.67 0.72 0.76 -0.45 -0.32 0.47];
x=x';
y=randn(14,1)
z=randn(14,1);
a=[1 2 3 4 5 6];
a=a';
b=[3 4 5 6 7 8];
b=b';
%c=[2 4 6 8 10 12];
%c=[-3 5 -17 9 34 -24];
c=randn(6,1)
c=c';
d=[4 2 7 9 2 6];
d=d';
[R1, P1]=corrcoef(a,b)
[R2, P2]=corrcoef(a,c)
[R3, P3]=corrcoef(a,d)
% [R P]=corrcoef(a,b)
[RY1, PY1]=corrcoef(x,y)
RY=corrcoef(x,y)
RZ=corrcoef(z,y)
[RZ1, PZ1]=corrcoef(z,y)
[Rr,Pr]=corrcoef(z,x)