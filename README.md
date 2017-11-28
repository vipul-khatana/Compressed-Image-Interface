# Compressed-Image-Interface
This code presents a method of compressing an image representation using linked lists 

## Overview of the task
Imagine a grayscale image of size NxN. Each pixel is either black (0) or white (1). In typical course, this will be represented as a 2D array storing O(N2) values. The idea of the compressed representation is to use the redundancy in the pixel value information among neighboring pixels to reduce the amount of information that needs to be stored. The representation proceeds row-wise and stores the column indices of contiguous segments of black (0) pixels.For example, see the image below:

<img src="Other/img.jpg" alt="Drawing" width="200" height="200"/>

Its uncompressed representation is as follows (first two numbers are image width and height):

16 16 
1111111111111111 
1111100011111111
1110000011111111 
1100000011111111 
1101110011111111 
1111110011111111
1111110011111111 1111000111111111 1100011111111111 1100111111111100 1101111111111000 1111111111100011 1111111111100111 1111111111001111 1111111110011111 1111111000111111

Its equivalent compressed representation is: 16 16

-1
5 7 -1
3 7 -1
2 7 -1
2 2 6 7 -1
6 7 -1
6 7 -1
4 6 -1
2 4 -1
2 3 14 15 -1 2 2 13 15 -1 11 13 -1
11 12 -1
10 11 -1
9 10 -1
7 9 -1

For every row in the image we store column indices for contiguous segments of black pixels. For example in the line corresponding to row 2 the set of black pixels are from column number 3 to 7. Hence we have 3 7 in the fourth line of the representation. The -1s demarcate the rows. If there are more than 1 contiguous black segments in a row then we store the start and end indices of each segment in order, as for rows 4, 9 and 10.

The ADT for the compressed image described in [CompressedImage.java]()


