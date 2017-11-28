# Compressed-Image-Interface
This code presents a method of compressing an image representation using linked lists 

## Overview 
Imagine a grayscale image of size NxN. Each pixel is either black (0) or white (1). In typical course, this will be represented as a 2D array storing O(N2) values. The idea of the compressed representation is to use the redundancy in the pixel value information among neighboring pixels to reduce the amount of information that needs to be stored. The representation proceeds row-wise and stores the column indices of contiguous segments of black (0) pixels.For example, see the image below:

<img src="Other/img.jpg" alt="Drawing" width="200" height="200"/>

Its uncompressed representation is as follows (first line is image width and height):

16 16 

1111111111111111 

1111100011111111

1110000011111111 

1100000011111111 

1101110011111111 

1111110011111111

