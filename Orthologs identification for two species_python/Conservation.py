# -*- coding: utf-8 -*-
import os
import numpy as np
from Bio import AlignIO
import matplotlib.pyplot as plt
import math
from collections import Counter


print(os.getcwd())
os.chdir("D:/KU Leuven/Third semester/Comparative and Regulatory Genomics/Comparative genomics/Assignment")

def read_File(filename,file_type):
    # Read the MSA file
    alignment = AlignIO.read(filename,file_type)
    
    # Get the number of sequences and the length of the alignment
    num_seqs = len(alignment)
    aln_len = alignment.get_alignment_length()
    
    # Create a matrix to store the data
    matrix = np.empty((num_seqs, aln_len), dtype=np.object)
    
    # Iterate over the rows and columns of the matrix
    for i, record in enumerate(alignment):
        for j in range(aln_len):
            matrix[i, j] = record.seq[j]
    
    
    # Print the matrix
    num_rows, num_cols = matrix.shape
    print(f'The matrix has {num_rows} rows and {num_cols} columns')
    # The matrix has 30 rows and 1425 columns
    return matrix

def get_Conservation(matrix):
    Simpson_Diversity = []
    Shannon_Entropy = []
    for i in range(len(matrix[0])):
        column = (matrix[:, i])
        # Count the number of occurrences of each category
        counts = Counter(column)

        N = len(column)
        D = 1 - sum(counts[x] * (counts[x] - 1) / (N * (N - 1)) for x in counts)
        H = -1 * sum(counts[x]/N * math.log2(counts[x]/N) for x in counts)
        Simpson_Diversity.append(D)
        Shannon_Entropy.append(H)
    return Simpson_Diversity, Shannon_Entropy

def get_Conservation_rate(list,threshold):
    count = 0
    for e in list:
        if e <= threshold:
            count += 1
    # print(count)
    conser_rate = count/len(list)
    # print(conser_rate)
    conser_rate = '%.2f%%' % (conser_rate * 100)
    return conser_rate


def visualization(sd,se,threshold1, threshold2):
    # plot 
    x = np.array(range(0,len(sd)))
    plt.figure()
    plt.scatter(x, sd, s=0.5)
    plt.figure()
    plt.scatter(x, se, s=0.5)
    
    conser_rate = get_Conservation_rate(sd, threshold1)
    print("Consevation rate calculated by Simpson Diversity method", conser_rate)
    
    conser_rate = get_Conservation_rate(se, threshold2)
    print("Consevation rate calculated by Shannon Entropy method", conser_rate)
    
    return 
    

# MSA for 29 protein sequences
matrix1 = read_File('29sequences.txt', 'fasta')
sd1, se1 = get_Conservation(matrix1)
visualization(sd1,se1,0,0)

# MSA for 26 nucleotide sequences for corresponding proteins
matrix2 = read_File('26promoter.txt', 'fasta')
sd2, se2 = get_Conservation(matrix2)
visualization(sd2,se2,0.5,1.0)

# MSA for 25 nucleotide sequences for corresponding proteins without Shuttleworthia satelles DSM 14600
matrix3 = read_File('25promoter.txt', 'fasta')
sd3, se3 = get_Conservation(matrix3)
visualization(sd3,se3,0.5,1.0)


