package com.arbiterli.model;

import java.util.Random;

public class Matrix {
	private double[][] matrix;
	private int row;
	private int column;
	
	public Matrix(int row, int column) {
		this.row = row;
		this.column = column;
		matrix = new double[row][column];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<row;++i) {
			for(int j=0;j<column;++j) {
				sb.append(matrix[i][j] + ",");
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * @param i means row
	 * @param j means column
	 * @return complement Matrix
	 */
	public Matrix getComplementMatrix(int i, int j) {
		Matrix complementMatrix = new Matrix(row - 1, column - 1);
		for(int k=0;k<row-1;++k) {
			for(int t=0;t<column-1;++t) {
				if(k<i && t<j) {
				    complementMatrix.setMatrixValue(getMatrixValue(k,t), k, t);
				} else if(k<i && t>=j) {
				    complementMatrix.setMatrixValue(getMatrixValue(k,t+1), k, t);
				} else if(k>=i && t<j) {
				    complementMatrix.setMatrixValue(getMatrixValue(k+1,t), k, t);
				} else {
				    complementMatrix.setMatrixValue(getMatrixValue(k+1,t+1), k, t);
				}
			}
		}
		return complementMatrix;
	}
	
	/**
	 * @return Companion Matrix
	 */
	public Matrix getCompanionMatrix() {
	    Matrix companionMatrix = new Matrix(row, column);
	    for(int i=0;i<row;++i){
	        for(int j=0;j<column;++j) {
	            companionMatrix.setMatrixValue(getSgn(i+j)*(getComplementMatrix(i,j).getDeterminant()), j, i);
	        }
	    }
	    return companionMatrix;
	}
	
    /**
     * @return inverse matrix
     */
	public Matrix getInverse() {
	    return Matrix.divide(getCompanionMatrix(), getDeterminant());
	}
	
	/**
	 * @return transfer matrix.
	 */
	public Matrix getTransfer() {
	    Matrix m = new Matrix(column, row);
	    for(int i=0;i<column;++i) {
	        for(int j=0;j<row;++j) {
	            m.setMatrixValue(matrix[j][i], i, j);
	        }
	    }
	    return m;
	}
	
	/**
	 * @return two paradigm.
	 */
	public double getParadigm() {
	    double re = 0;
	    
	    if(column == 1) {
	        for(int i=0;i<row;++i) {
	            re += matrix[i][0] * matrix[i][0];
	        }
	    } else if(row == 1) {
	        for(int i=0;i<column;++i) {
                re += matrix[0][i] * matrix[0][i];
            }
	    } else {
	        System.out.println("can not get paradigm from non-vector.");
            return 0;
	    }
	    return Math.sqrt(re);
	}
	
	/**
	 * @return determinant
	 */
	public double getDeterminant() {
		double determinant = 0;
		if(row != column) {
		    System.out.println("can not get determinant.");
		    return 0;
		}
		if(row == 1 && column == 1) {
			return matrix[0][0];
		}
		for(int j=0;j<column;++j) {
			determinant += getSgn(j)*matrix[0][j]
			              *(getComplementMatrix(0,j).getDeterminant());
		}
		return determinant;
	}
	
	public static Matrix plus(Matrix m1, Matrix m2) {
	    if(m1.getRow() != m2.getRow() || m1.getColumn() != m2.getColumn()) {
	        System.out.println("Matrix Plus requires same row and column.");
	        return null;
	    }
	    int row = m1.getRow();
	    int column = m1.getColumn();
	    Matrix m = new Matrix(row, column);
	    for(int i=0;i<row;++i) {
	        for(int j=0;j<column;++j) {
	            m.setMatrixValue(m1.getMatrixValue(i, j) + m2.getMatrixValue(i, j), i, j);
	        }
	    }
	    return m;
	}
	
	public static Matrix minus(Matrix m1, Matrix m2) {
        if(m1.getRow() != m2.getRow() || m1.getColumn() != m2.getColumn()) {
            System.out.println("Matrix minus requires same row and column.");
            return null;
        }
        int row = m1.getRow();
        int column = m1.getColumn();
        Matrix m = new Matrix(row, column);
        for(int i=0;i<row;++i) {
            for(int j=0;j<column;++j) {
                m.setMatrixValue(m1.getMatrixValue(i, j) - m2.getMatrixValue(i, j), i, j);
            }
        }
        return m;
    }
	
	public static Matrix multi(Matrix m1, Matrix m2) {
	    if(m1.getColumn() != m2.getRow()) {
	        System.out.println("can not multi.");
	        return null;
	    }
	    int row = m1.getRow();
        int column = m2.getColumn();
        double sum = 0;
        Matrix m = new Matrix(row, column);
        for(int i=0;i<row;++i) {
            for(int j=0;j<column;++j) {
                sum = 0;
                for(int k=0;k<m1.getColumn();++k) {
                    sum += m1.getMatrixValue(i, k) * m2.getMatrixValue(k, j);
                }
                m.setMatrixValue(sum, i, j);
            }
        }
        return m;
	}
	
	public static Matrix divide(Matrix m1, double d) {
	    if(d == 0) {
	        System.out.println("divide by 0.");
	        return null;
	    }
	    int row = m1.getRow();
        int column = m1.getColumn();
        Matrix m = new Matrix(row, column);
        for(int i=0;i<row;++i) {
            for(int j=0;j<column;++j) {
                m.setMatrixValue(m1.getMatrixValue(i, j) / d, i, j);
            }
        }
        return m;
	}
	
	public static Matrix multi(Matrix m1, double d) {
        int row = m1.getRow();
        int column = m1.getColumn();
        Matrix m = new Matrix(row, column);
        for(int i=0;i<row;++i) {
            for(int j=0;j<column;++j) {
                m.setMatrixValue(m1.getMatrixValue(i, j) * d, i, j);
            }
        }
        return m;
    }
	
	public static Matrix getRandom(int row, int column) {
		Matrix m = new Matrix(row, column);
		Random r = new Random();
		for(int i=0;i<row;++i) {
			for(int j=0;j<column;++j) {
				m.setMatrixValue(r.nextDouble(), i, j);
			}
		}
		return m;
	}
	
	public static Matrix getI(int row) {
	    Matrix m = new Matrix(row, row);
	    for(int i=0;i<row;++i) {
	        m.setMatrixValue(1, i, i);
	    }
	    return m;
	}
	
	public int getSgn(int k) {
		return ((k&1) != 0 ? -1 : 1);
	}
	
	public void setMatrixValue(double value, int i, int j) {
		matrix[i][j] = value;
	}
	
	public double getMatrixValue(int i, int j) {
		return matrix[i][j];
	}

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
	
}
