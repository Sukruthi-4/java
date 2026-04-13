import java.io.*;
import java.net.*;
import java.util.*;

// Unified Assignment Program
public class SmartAssignment {

    // -------------------- TREE STRUCTURE --------------------
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // -------------------- MAIN MENU --------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== ASSIGNMENT MENU =====");
            System.out.println("1. Print Nodes at Distance K");
            System.out.println("2. Bitcoin INR Value in Words");
            System.out.println("3. Orchard Size Finder");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    runTreeProblem(sc);
                    break;
                case 2:
                    runBitcoinProblem();
                    break;
                case 3:
                    runOrchardProblem();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // -------------------- PROBLEM 1 --------------------
    static void runTreeProblem(Scanner sc) {
        // Sample tree (can modify if needed)
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.print("Enter value of k: ");
        int k = sc.nextInt();

        System.out.print("Nodes at distance " + k + ": ");
        traverseWithLevel(root, 0, k);
        System.out.println();
    }

    static void traverseWithLevel(TreeNode node, int level, int target) {
        if (node == null) return;

        if (level == target) {
            System.out.print(node.val + " ");
            return;
        }

        traverseWithLevel(node.left, level + 1, target);
        traverseWithLevel(node.right, level + 1, target);
    }

    // -------------------- PROBLEM 2 --------------------
    static void runBitcoinProblem() {
        try {
            String api = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=inr";
            HttpURLConnection conn = (HttpURLConnection) new URL(api).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String json = br.readLine();

            // Extract only numbers (unique approach)
            String num = json.replaceAll("[^0-9]", "");
            int value = Integer.parseInt(num);

            System.out.println("Bitcoin Price (INR): " + value);
            System.out.println("In Words: " + numberToWords(value));

        } catch (Exception e) {
            System.out.println("Error fetching Bitcoin data.");
        }
    }

    static String numberToWords(int n) {
        if (n == 0) return "Zero";
        return convert(n).trim();
    }

    static String convert(int n) {
        String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six",
                         "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
                         "Thirteen", "Fourteen", "Fifteen", "Sixteen",
                         "Seventeen", "Eighteen", "Nineteen"};

        String[] tens = {"", "", "Twenty", "Thirty", "Forty",
                         "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        if (n < 20) return ones[n];
        if (n < 100) return tens[n / 10] + " " + convert(n % 10);
        if (n < 1000) return ones[n / 100] + " Hundred " + convert(n % 100);
        if (n < 100000) return convert(n / 1000) + " Thousand " + convert(n % 1000);
        if (n < 10000000) return convert(n / 100000) + " Lakh " + convert(n % 100000);

        return convert(n / 10000000) + " Crore " + convert(n % 10000000);
    }

    // -------------------- PROBLEM 3 --------------------
    static void runOrchardProblem() {

        char[][] grid = {
            {'O','T','O','O'},
            {'O','T','O','T'},
            {'T','T','O','T'},
            {'O','T','O','T'}
        };

        boolean[][] visited = new boolean[grid.length][grid[0].length];

        System.out.print("Orchard sizes: ");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (grid[i][j] == 'T' && !visited[i][j]) {
                    System.out.print(bfs(grid, visited, i, j) + " ");
                }
            }
        }
        System.out.println();
    }

    static int bfs(char[][] grid, boolean[][] visited, int i, int j) {

        int[][] dirs = {
            {-1,-1},{-1,0},{-1,1},
            {0,-1},        {0,1},
            {1,-1},{1,0},{1,1}
        };

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i, j});
        visited[i][j] = true;

        int count = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            count++;

            for (int[] d : dirs) {
                int x = cur[0] + d[0];
                int y = cur[1] + d[1];

                if (isValid(grid, visited, x, y)) {
                    visited[x][y] = true;
                    q.add(new int[]{x, y});
                }
            }
        }
        return count;
    }

    static boolean isValid(char[][] grid, boolean[][] visited, int x, int y) {
        return x >= 0 && y >= 0 &&
               x < grid.length && y < grid[0].length &&
               grid[x][y] == 'T' && !visited[x][y];
    }
}
