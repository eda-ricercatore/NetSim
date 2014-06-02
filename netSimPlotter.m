% This is a MATLAB script to plot the simulation results from NetSim,
% which optimizes a generic telecommunications network
% Author: Zhiyang Ong and Andy Hao-Wei Lo

% Reference: Stephan J. Chapman, "MATLAB Programming for
% Engineers," Brooks/Cole, Toronto, 2000, pp. 289 - 322

% Standard file IDs
% : file id 1 is the standard output device (stdout)
% : file id 2 is the standard error device (stderr)

% Assume that the values for each row are separated by character
% white spaces

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

disp('Welcome to NetSim Plotters...')

% Assume that the file does not exists initially
file = 0;

while file == 0
    % Prompt the user to enter a filename containing the simulation results
    filename = input('Enter a filename containing the simulation results: \n','s');

    % If this file cannot be opened, prompt the user to select another file
    file = exist(filename,'file');
    if file == 2
        disp(['The requested file exists: ' filename])
    elseif file == 0
        disp(['The requested file does not exist: ' filename])
    end
end

% Prompt the user to enter the number of cost functions used
numColumns = input('Enter the number of cost functions used: \n');
numCostFns = numColumns;
% Include the columns containing the number of generations
% evolved, and the values of pleiotropy and redundancy,
% and the best Chromosome's and average population's fitness values
numColumns = 2*numColumns + 3;

cf1=' cost of min spanning tree';
cf2=' average distance between any two nodes';
cf3=' total cost of all edges';
cf4=' average degree of separation';
cf5=' maximum degree of separation';
cf6=' load factor';
cf7=' average clustering coefficient';
cf8=' network resistance';
cf = {cf1, cf2, cf3, cf4, cf5, cf6, cf7, cf8};

for cfIndex = 1:numCostFns
    query='Enter the number associated with the selected cost functions\n';
    number=input(query);
    titleArr(cfIndex)=cf(number);
end

%-----------------------------------------------------------------

% Read the results file... get its file ID
% That is, you cannot write to this file
%fid = fopen(filename,'r')
fid = fopen(filename);

% Read formatted data in the user selected file
[array,count] = fscanf(fid,'%f')

% Determine the number of rows in the file
count;
numColumns;
numRows = count / numColumns;

%dummy=0;
%for di=2:numRows
    %dummy=[dummy 0];
%end
%fitness=[dummy' dummy' dummy'];

% Put the data into the same format as the file
ARR = reshape(array,numColumns,numRows);
array=ARR';

% Get the numbers of generations that have evolved
for row = 1:numRows
    numGen(row) = array(row,1);
end
%numGen = numGen'

% Get the values of pleiotropy for each generation
for row = 1:numRows
    pleiotropy(row) = array(row,2);
end
%pleiotropy = pleiotropy'

% Get the values of redundancy for each generation
for row = 1:numRows
    redundancy(row) = array(row,3);
end
%redundancy = redundancy'

figure(1);
% Plot the values of pleiotropy and redundancy versus
% the number of generations evolved
plot(numGen,pleiotropy,'b-');
hold on;
plot(numGen,redundancy,'r-')
title('\bfPlot of pleiotropy and redundancy against generations evolved');
xlabel('numGen');
ylabel('pleiotropy & redundancy');
legend('pleiotropy', 'redundancy');
hold off;

y1=' Plot of ';
y2=' against generations evolved';

% Get the values of the ith cost function for each generation
%numCostFns = numCostFns;
for cost = 1:numCostFns
    for row = 1:numRows
        % Average fitness values of the chromosome
        costFnAvg(row) = array(row,cost*2+2);
        % Fitness value of the top chromosome
        costFnBest(row) = array(row,cost*2+3);
    end

    % Use (cost+1) since we already used Figure 1
    figure(cost+1);
    % Plot the total cost of the minimum spanning tree versus
    % the number of generations evolved
    plot(numGen,costFnAvg,'b-');
    hold on;
    plot(numGen,costFnBest,'r-');
    % Store the best chromosome's fitness in an array
    if cost == 1
        fitness=costFnBest';
    else
        fitness=[fitness costFnBest'];
    end
    %tt=strcat(y1,cf(cost),y2);
    title(strcat(y1,titleArr(cost),y2));
    xlabel('numGen');
    ylabel(titleArr(cost));
    legend('Average values','Best values');
    %hold off;
end

% Determine the correlation between the cost functions
cap=numCostFns + 1;
m=1;
for i=1:numCostFns
    j=i+1;
    while j < cap
        %[i,j]=corrcoef(fitness(i),fitness(j))
        %correlation(m) = corrcoef(fitness(i),fitness(j));
        for ai=1:numRows
            fitnessA(ai)=fitness(ai,i);
        end
        %fitnessA=fitnessA';
        for bi=1:numRows
            fitnessB(bi)=fitness(bi,j);
        end
        %fitnessB=fitnessB';
        %correlation(m) = corrcoef(fitnessA,fitnessB);
        [corr pVal]=corrcoef(fitnessA,fitnessB)
        m = m+1;
        j=j+1;
    end
end

% Detach all file IDs from disk files and devices
% - make non-standard file IDs invalid
fclose('all');