import java.io.*;

public class HuffmanEncoder {

    private String inputFileName;
    private String outputFileName;
    private String codesFileName;
    private BookReader book;
    private MyOrderedList<FrequencyNode> frequencies = new MyOrderedList<FrequencyNode>();
    private HuffmanNode huffmanTree;
    private MyOrderedList<CodeNode> codes = new MyOrderedList<>();
    private byte[] encodedText;
    public HuffmanEncoder() throws IOException {
        inputFileName = "WarAndPeace.txt";
        outputFileName = "WarAndPeace-compressed.bin";
        codesFileName = "WarAndPeace-codes.txt";
        book = new BookReader(inputFileName);
        countFrequency();
        buildTree();
        encode();
        writeFiles();
    }
    private void countFrequency() {
        long start, finish, difference;
        start = System.currentTimeMillis();    // Starts timer.
        System.out.println("Counting frequencies...");
        for (int i = 0; i < book.book.length(); i++) {
            FrequencyNode node = new FrequencyNode();
            node.character = book.book.charAt(i);
            FrequencyNode existingNode = frequencies.binarySearch(node);
            if (existingNode == null) {
                frequencies.add(node);
            } else {
                existingNode.frequency++;
            }
        }
        System.out.println(frequencies.size() + " unique characters found.");
        finish = System.currentTimeMillis(); // Ends timer.
        difference = finish - start; // Calculates the time to process.
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }
    private void buildTree() {
        long start, finish, difference;
        start = System.currentTimeMillis();    // Starts timer.
        System.out.println("Building tree and reading codes...");
        MyPriorityQueue<HuffmanNode> queue = new MyPriorityQueue<>();
        for (int i = 0; i < frequencies.size(); i++) {
            HuffmanNode node = new HuffmanNode(frequencies.get(i).character, frequencies.get(i).frequency);
            queue.insert(node);
        }
        while (queue.size() > 1) {
            HuffmanNode left = queue.removeMin();
            HuffmanNode right = queue.removeMin();
            HuffmanNode parent = new HuffmanNode(left, right);
            huffmanTree = parent;
            queue.insert(parent);
        }
        extractCodes(huffmanTree, "");
        finish = System.currentTimeMillis(); // Ends timer.
        difference = finish - start; // Calculates the time to process.
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }

    private void extractCodes(HuffmanNode huffmanTree, String code) {
        if(huffmanTree.left == null && huffmanTree.right == null) {
            CodeNode node = new CodeNode();
            node.character = huffmanTree.character;
            node.code = code;
            codes.add(node);
            return;
        }
        extractCodes(huffmanTree.left, code + "0");
        extractCodes(huffmanTree.right, code + "1");
    }

    private void encode() {
        long start, finish, difference;
        start = System.currentTimeMillis();    // Starts timer.
        System.out.println("Encoding...");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < book.book.length(); i++) {
            for (int j = 0; j < codes.size(); j++) {
                if (book.book.charAt(i) == codes.get(j).character) {
                    String code = codes.get(j).code;
                    sb.append(code);
                }
            }
        }
        int encodedTextLength = sb.length()/8;
        encodedText = new byte[sb.length()%8 ==0 ? encodedTextLength : encodedTextLength + 1];
        int j = 0;
        int index = 0;
        sb.toString();
        for (int i = 0; i < sb.length(); i += j) {
            j = i + 8 < sb.length() ? 8 : sb.length() - i;
            encodedText[index++] = (byte) Integer.parseInt(sb.substring(i, i + j), 2);
        }
        finish = System.currentTimeMillis(); // Ends timer.
        difference = finish - start; // Calculates the time to process.
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }
    private void writeFiles() throws IOException {
        long start, finish, difference;
        start = System.currentTimeMillis();    // Starts timer.
        System.out.println("Writing files...");
        FileOutputStream output = new FileOutputStream(new File(outputFileName));
        try (PrintWriter writer = new PrintWriter(new File(codesFileName))) {
            writer.print("");
            for (int i = 0; i < codes.size(); i++) {
                writer.append(codes.get(i).toString() + (i != codes.size() - 1 ? "\n" : ""));
            }
            output.write(encodedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(encodedText.length + " bytes written.");
        finish = System.currentTimeMillis(); // Ends timer.
        difference = finish - start; // Calculates the time to process.
        System.out.println("Time to process: " + difference + " milliseconds.");
        System.out.println();
    }

    private class HuffmanNode implements Comparable<HuffmanNode> {
        public char character;
        public int weight;
        public HuffmanNode left;
        public HuffmanNode right;

        public HuffmanNode(char character, int weight) {
            this.character = character;
            this.weight = weight;
            left = null;
            right = null;
        }

        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.left = left;
            this.right = right;
            character = '\0';
            this.weight = left.weight + right.weight;
        }

        public int compareTo(HuffmanNode other) {
            return this.weight - other.weight;
        }

        public String toString() {
            return character + ": " + weight;
        }
    }

    private class CodeNode implements Comparable<CodeNode> {
        public char character;
        public String code;

        public int compareTo(CodeNode other) {
            return this.character - other.character;
        }

        public String toString() {
            return character + ": " + code;
        }
    }

    private class FrequencyNode implements Comparable<FrequencyNode> {
        public int frequency = 1;
        public char character;

        @Override
        public int compareTo(FrequencyNode o) {
            return character - o.character;
        }

        @Override
        public String toString() {
            return character + ": " + frequency;
        }
    }
}