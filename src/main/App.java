package main;
import java.util.*;

import entity.Buyer;
import entity.Product;
import entity.Purchase;

import tools.Input;
public class App {
    private List<Product> productList = new ArrayList<>();
    private List<Buyer> buyerList = new ArrayList<>();
    private List<Purchase> purchaseList = new ArrayList<>();


    void run() {
        boolean repeat = true;
        System.out.println("------ Musical Instruments Shop ------");
        do {
            System.out.println("List tasks:");
            System.out.println("0. Exit\n" +
                    "1. Add a product\n" +
                    "2. Add a buyer\n" +
                    "3. List of buyers\n" +
                    "4. Product list\n" +
                    "5. Purchase the product by the user\n" +
                    "6. List of purchased products for the selected user\n" +
                    "7. Add money to the user\n" +
                    "8. Display the cost of sold items\n" +
                    "9. Display top buyers\n" +
                    "10. Display top sold items");
            System.out.println("Task number: ");
            int task = Input.inputNumberFromRange(0, 10);

            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    addProduct();
                    break;
                case 2:
                    addBuyer();
                    break;
                case 3:
                    listBuyers();
                    break;
                case 4:
                    listProducts();
                    break;
                case 5:
                    purchaseProduct();
                    break;
                case 6:
                    listPurchasedProducts();
                    break;
                case 7:
                    addMoneyToBuyer();
                    break;
                case 8:
                    displayTotalSales();
                    break;
                case 9:
                    topBuyers();
                    break;
                case 10:
                    topSoldItems();
                    break;
                default:
                    System.out.println("no such option!");
            }
        } while (repeat);
    }

    private void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter product type: ");
        String type = scanner.nextLine();

        System.out.println("Enter product name: ");
        String name = scanner.nextLine();

        System.out.println("Enter product price: ");
        double price = scanner.nextDouble();

        System.out.println("Enter product stock: ");
        int stock = scanner.nextInt();

        Product product = new Product(type, name, price, stock);
        productList.add(product);

        System.out.println("Product added successfully!");
    }

    private void addBuyer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter buyer name: ");
        String name = scanner.nextLine();

        System.out.println("Enter buyer second name: ");
        String email = scanner.nextLine();

        System.out.println("Enter buyer phone number: ");
        String address = scanner.nextLine();

        Buyer buyer = new Buyer(name, email, address);
        buyerList.add(buyer);

        System.out.println("Buyer added successfully!");
    }

    private void listBuyers() {
        System.out.println("Buyers list:");
        for (int i = 0; i < buyerList.size(); i++) {
            System.out.println(i + 1 + ". " + buyerList.get(i));
        }
    }

    private void listProducts() {
        System.out.println("Products list:");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(i + 1 + ". " + productList.get(i));
        }
    }

    private void purchaseProduct() {

        System.out.println("Select a buyer (buyer number):");
        listBuyers();
        int buyerIndex = Input.inputNumberFromRange(1, buyerList.size()) - 1;

        System.out.println("Select a product (product number):");
        listProducts();
        int productIndex = Input.inputNumberFromRange(1, productList.size()) - 1;

        Buyer buyer = buyerList.get(buyerIndex);
        Product product = productList.get(productIndex);

        if (buyerHasEnoughMoney(buyer, product) && product.getStock() > 0) {
            buyer.deductMoney(product.getPrice());
            product.setStock(product.getStock() - 1);
            Purchase purchase = new Purchase(buyer, product);
            purchaseList.add(purchase);
            System.out.println("Purchase successful! Updated stock: " + product.getStock());
        }
        else if (product.getStock() == 0) {
            System.out.println("Out of stock!");
        }
        else {
            System.out.println("Not enough money!");
        }
    }

    private void listPurchasedProducts() {
        System.out.println("Select a buyer to list purchased products (buyer number) :");
        listBuyers();
        int buyerIndex = Input.inputNumberFromRange(1, buyerList.size()) - 1;

        Buyer buyer = buyerList.get(buyerIndex);

        System.out.println("Purchased products for " + buyer.getName() + ":");
        for (Purchase purchase : purchaseList) {
            if (purchase.getBuyer().equals(buyer)) {
                System.out.println(purchase.getProduct());
            }
        }
    }

    private void displayTotalSales() {
        double totalSales = 0.0;
        for (Purchase purchase : purchaseList) {
            totalSales += purchase.getProduct().getPrice();
        }
        System.out.println("Total sales amount: $" + totalSales);
    }

    private boolean buyerHasEnoughMoney(Buyer buyer, Product product) {
        return buyer != null && product != null && buyer.getMoney() >= product.getPrice();
    }


    private void addMoneyToBuyer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a buyer to add money (buyer number):");
        listBuyers();
        int buyerIndex = Input.inputNumberFromRange(1, buyerList.size()) - 1;

        Buyer buyer = buyerList.get(buyerIndex);

        System.out.println("Enter the amount of money to add: ");
        double amount = scanner.nextDouble();

        buyer.addMoney(amount);
        System.out.println("Money added successfully to " + buyer.getName());
    }

    private void topBuyers() {
        System.out.println("Top Buyers:");

        String[] buyerNames = new String[buyerList.size()];
        int[] itemsBought = new int[buyerList.size()];

        for (int i = 0; i < buyerList.size(); i++) {
            buyerNames[i] = buyerList.get(i).getName();
        }

        for (Purchase purchase : purchaseList) {
            int buyerIndex = buyerList.indexOf(purchase.getBuyer());
            itemsBought[buyerIndex]++;
        }

        for (int i = 0; i < buyerList.size() - 1; i++) {
            for (int j = i + 1; j < buyerList.size(); j++) {
                if (itemsBought[j] > itemsBought[i]) {
                    int tempItems = itemsBought[i];
                    itemsBought[i] = itemsBought[j];
                    itemsBought[j] = tempItems;

                    String tempName = buyerNames[i];
                    buyerNames[i] = buyerNames[j];
                    buyerNames[j] = tempName;
                }
            }
        }

        int topBuyersCount = Math.min(5, buyerList.size());
        for (int i = 0; i < topBuyersCount; i++) {
            System.out.println((i + 1) + ". " + buyerNames[i] + " - Items Bought: " + itemsBought[i]);
        }
    }
    private void topSoldItems() {
        System.out.println("Top Sold Items:");

        String[] productNames = new String[productList.size()];
        int[] quantitySold = new int[productList.size()];

        for (int i = 0; i < productList.size(); i++) {
            productNames[i] = productList.get(i).getName();
        }

        for (Purchase purchase : purchaseList) {
            int productIndex = productList.indexOf(purchase.getProduct());
            quantitySold[productIndex]++;
        }

        for (int i = 0; i < productList.size() - 1; i++) {
            for (int j = i + 1; j < productList.size(); j++) {
                if (quantitySold[j] > quantitySold[i]) {
                    int tempQuantity = quantitySold[i]; // меняем переменные местами
                    quantitySold[i] = quantitySold[j];
                    quantitySold[j] = tempQuantity;

                    String tempName = productNames[i];
                    productNames[i] = productNames[j];
                    productNames[j] = tempName;
                }
            }
        }

        int topItemsCount = Math.min(5, productList.size());
        for (int i = 0; i < topItemsCount; i++) {
            System.out.println((i + 1) + ". " + productNames[i] + " - Quantity Sold: " + quantitySold[i]);
        }
    }

    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.txt"))) {
            // Save products
            for (Product product : productList) {
                writer.println("Product," + product.getType() + "," + product.getName() + "," + product.getPrice() + "," + product.getStock());
            }
            // Save buyers
            for (Buyer buyer : buyerList) {
                writer.println("Buyer," + buyer.getName() + "," + buyer.getSecondName() + "," + buyer.getPhoneNumber() + "," + buyer.getMoney());
                // Save purchased products for each buyer
                for (Purchase purchase : purchaseList) {
                    if (purchase.getBuyer().equals(buyer)) {
                        writer.println("Purchase," + productList.indexOf(purchase.getProduct()));
                    }
                }
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    switch (parts[0]) {
                        case "Product":
                            productList.add(new Product(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4])));
                            break;
                        case "Buyer":
                            Buyer buyer = new Buyer(parts[1], parts[2], parts[3]);
                            buyer.setMoney(Double.parseDouble(parts[4]));
                            buyerList.add(buyer);
                            break;
                        case "Purchase":
                            int productIndex = Integer.parseInt(parts[1]);
                            if (!buyerList.isEmpty()) {
                                Purchase purchase = new Purchase(buyerList.get(buyerList.size() - 1), productList.get(productIndex));
                                purchaseList.add(purchase);
                            }
                            break;
                        default:
                            System.out.println("Unknown entry in file");
                            break;
                    }
                }
            }
            System.out.println("Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }


}
