package org.program;

import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Fazer um programa para ler o caminho de um arquivo .csv
//        contendo os dados de itens vendidos.
//
//        Cada item possui um nome, preço unitário e quantidade, separados por vírgula. Você
//        deve gerar um novo arquivo chamado "summary.csv", localizado
//        em uma subpasta chamada "out" a partir da pasta original do
//            arquivo de origem, contendo apenas o nome e o valor total para
//        aquele item (preço unitário multiplicado pela quantidade),
//        conforme exemplo.

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> ls = new ArrayList<Product>();

        System.out.println("Enter file path: ");
        String path = sc.nextLine();

        File file = new File(path);

        String fileParent = file.getParent();

        boolean sucess = new File(fileParent + "\\out").mkdir();

        System.out.println("File created: " + sucess);

        String pathOut = fileParent + "\\out\\sumary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String itemCsv = br.readLine();
            System.out.println("Source file:");
            while (itemCsv != null) {
                System.out.println(itemCsv);

                String[] items = itemCsv.split(",");

                String name = items[0];
                double price = Double.parseDouble(items[1]);
                int quantity = Integer.parseInt(items[2]);

                ls.add(new Product(name, price, quantity));

                itemCsv = br.readLine();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathOut))) {
                for (Product item : ls) {
                    bw.write(item.getName() + "," + String.format("%.2f", item.total()));
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        sc.close();
    }
}