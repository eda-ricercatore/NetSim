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

% Assume that the files does not exists initially
% File containing fitness result of the first cost function
file1 = 0;

while file1 == 0
    % Prompt the user to enter a filename containing the fitness result of the first cost function
    filename1 = input('Enter a filename containing the fitness result of the 1st cost function: \n','s');

    % If this file cannot be opened, prompt the user to select another file
    file1 = exist(filename1,'file');
    if file1 == 2
        disp(['The requested file exists: ' filename1])
    elseif file1 == 0
        disp(['The requested file does not exist: ' filename1])
    end
end

file2 = 0;

while file2 == 0
    % Prompt the user to enter a filename containing the fitness result of the second cost function
    filename2 = input('Enter a filename containing the fitness result of the 2nd cost function: \n','s');

    % If this file cannot be opened, prompt the user to select another file
    file2 = exist(filename2,'file');
    if file2 == 2
        disp(['The requested file exists: ' filename2])
    elseif file2 == 0
        disp(['The requested file does not exist: ' filename2])
    end
end

file3 = 0;

while file3 == 0
    % Prompt the user to enter a filename containing the fitness result of the 3rd cost function
    filename3 = input('Enter a filename containing the fitness result of the 3rd cost function: \n','s');

    % If this file cannot be opened, prompt the user to select another file
    file3 = exist(filename3,'file');
    if file3 == 2
        disp(['The requested file exists: ' filename3])
    elseif file3 == 0
        disp(['The requested file does not exist: ' filename3])
    end
end

%-----------------------------------------------------------------

% Read file1... get its file ID
% That is, you cannot write to this file
fid1 = fopen(filename1);

% Read formatted data in file1
[array1,count1] = fscanf(fid1,'%f')

% Read file2... get its file ID
% That is, you cannot write to this file
fid2 = fopen(filename2);

% Read formatted data in file2
[array2,count2] = fscanf(fid2,'%f')

% Read file3... get its file ID
% That is, you cannot write to this file
fid3 = fopen(filename3);

% Read formatted data in file3
[array3,count3] = fscanf(fid3,'%f')

% Determine the number of rows in the file
numColumns = 5;     % column number of the best fitness values
numRows1 = count1 / numColumns;
numRows2 = count2 / numColumns;
numRows3 = count3 / numColumns;

% Put the data into the same format as the file
ARR1 = reshape(array1,numColumns,numRows1);
array1=ARR1';
ARR2 = reshape(array2,numColumns,numRows2);
array2=ARR2';
ARR3 = reshape(array3,numColumns,numRows3);
array3=ARR3';

% Get the fitness values of the first cost function
for row = 1:numRows1
    costfunction1(row) = array1(row,numColumns);
end

% Get the fitness values of the second cost function
for row = 1:numRows2
    costfunction2(row) = array2(row,numColumns);
end

% Get the fitness values of the 3rd cost function
for row = 1:numRows3
    costfunction3(row) = array3(row,numColumns);
end

[corr pVal]=corrcoef(costfunction1,costfunction2,costfunction3)

% Detach all file IDs from disk files and devices
% - make non-standard file IDs invalid
fclose('all');