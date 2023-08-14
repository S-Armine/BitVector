import java.io.*;
import java.util.Vector;

public class BitVector {
    private byte[] vector;
    private int size;


    public BitVector(int initialSize) {
        if (initialSize % 8 == 0) {
            vector = new byte[initialSize / 8];
        } else {
            vector = new byte[initialSize / 8 + 1];
        }
        this.size = initialSize;
    }

     public BitVector(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String vectorString = reader.readLine().replace("_", "");
            reader.close();
            this.size = vectorString.length();
            if (this.size % 8 == 0) {
                vector = new byte[this.size / 8];
            } else {
                vector = new byte[this.size / 8 + 1];
            }
            for (int i = 0; i < vectorString.length(); i++) {
                if(vectorString.charAt(i) != '0' && vectorString.charAt(i) != '1')
                    throw new RuntimeException();
                if(vectorString.charAt(i) == '1')
                    this.set(vectorString.length() - i - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void set(int index) {
        indexOutOfBounds(index);
        if (!isSet(index)) {
            vector[vector.length - 1 - index / 8] |= (1 << (index % 8));
        }
    }

    public void reset(int index) {
        indexOutOfBounds(index);
        if (isSet(index)) {
            vector[vector.length - 1 - index / 8] ^= (1 << (index % 8));
        } else {
            System.out.println("Bit at this index is already at state of reset.");
        }
        if (index == this.size - 1) {
            System.out.println("You have reset sign bit.");
        }
    }

    public boolean isSet(int index) {
        return !((vector[vector.length - 1 - index / 8] & (1 << (index % 8))) == 0);
    }

    public void viewBitVector() {
        System.out.println(bitVectorToString());
    }

    public String bitVectorToString() {
        StringBuilder vectorToString = new StringBuilder();
        String strToAdd;
        for (int i = 0; i < vector.length; i++) {
            strToAdd = String.format("%8s", Integer.toBinaryString(vector[i])).replace(' ', '0');
            if (i == 0 && this.size % 8 != 0) {
                vectorToString.append(strToAdd.substring(8 - this.size % 8) + "_");
            } else {
                vectorToString.append(strToAdd.substring(strToAdd.length() - 8) + "_");
            }
        }
        vectorToString.replace(vectorToString.length() - 1, vectorToString.length(), "");
        return vectorToString.toString();
    }

    private void indexOutOfBounds(int index) {
        if (index < 0 || index >= this.size) {
            throw new BitVectorIndexOutOfBoundsException();
        }
    }

    public long toSignedDecimalValue() {
        String bitVector = this.bitVectorToString().replace("_", "");
        long decimal;
        if (bitVector.charAt(0) == '1') {
            bitVector = bitVector.replace('0', '2').replace('1', '0').replace('2', '1');
            decimal = (Long.parseLong(bitVector, 2) + 1) * -1;
        } else {
            decimal = Long.parseLong(bitVector, 2);
        }
        return decimal;
    }

    public long toUnsignedDecimalValue() {
        return Long.parseLong(bitVectorToString().replace("_", ""), 2);
    }

    public void zeroExtension(int extendBy) {
        int newSize = this.size + extendBy;
        if (this.size / 8 != newSize / 8) {
            byte[] temp = this.vector;
            if (newSize % 8 == 0) vector = new byte[newSize / 8];
            else vector = new byte[newSize / 8 + 1];
            int ind = this.vector.length - 1;
            for (int i = temp.length - 1; i >= 0; i--) {
                vector[ind--] = temp[i];
            }
        }
        this.size = newSize;
    }

    public void signExtension(int extendBy) {
        zeroExtension(extendBy);
        if (bitVectorToString().replace("_", "").charAt(extendBy) == '1') {
            for (int i = 0; i < extendBy; i++) {
                set(this.size - 1 - i);
            }
        }
    }

    public void writeToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.append(bitVectorToString());
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
