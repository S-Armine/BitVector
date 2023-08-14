// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        BitVector bv = new BitVector(27);
        bv.viewBitVector();
        bv.set(2);
        bv.viewBitVector();
        bv.set(15);
        bv.viewBitVector();
        bv.reset(2);
        bv.viewBitVector();
        bv.set(26);
        bv.set(11);
        bv.viewBitVector();
        bv.writeToFile("example.txt");
        System.out.println(bv.toSignedDecimalValue());
        for (int i = 0; i < 27; i++) bv.set(i);
        System.out.println(bv.toSignedDecimalValue());
        System.out.println(bv.toUnsignedDecimalValue());
        bv.viewBitVector();
        bv.signExtension(8);
        bv.viewBitVector();
        BitVector bv1 = new BitVector("example.txt");
        System.out.println(bv1.toSignedDecimalValue());
        bv1.reset(26);
        bv1.viewBitVector();
        bv1.writeToFile("example.txt");
    }
}