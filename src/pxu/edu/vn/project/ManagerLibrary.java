package pxu.edu.vn.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;

public class ManagerLibrary {
	static final String URI_SAVE_FILE = "/Users/lctiendat/Documents/books.txt";
	static final String DATA_NOT_FOUND = "Data not found";
	static final String FIND_ERROR = "Not find data with code : ";
	static final String INPUT_ERROR = "Enter input incorrect, error : ";
	static int[] code;
	static String[] name;
	static int[] releaseYear;
	static String[] author;
	static int[] quantity;
	static int[] price;
	static int x = 0;

	static final String CASE_ADD_BOOK = "1";
	static final String CASE_EDIT_BOOK = "2";
	static final String CASE_DELETE_BOOK = "3";
	static final String CASE_FIND_BOOK = "4";
	static final String CASE_ARRANGER_BOOK = "5";
	static final String CASE_EXPORT_FILE_BOOK = "6";
	static final String CASE_IMPORT_FILE_BOOK = "7";
	static final String CASE_EXIT_PROGRAM = "q";

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		String key = null;
		do {
			displayMenu();
			System.out.print("Your choice: ");
			key = sc.next();
			switch (key) {
				case CASE_ADD_BOOK:
					x = enterTheQuantity();
					code = new int[x];
					name = new String[x];
					releaseYear = new int[x];
					author = new String[x];
					quantity = new int[x];
					price = new int[x];
					create();
					show();
					break;
				case CASE_EDIT_BOOK:
					edit();
					break;
				case CASE_DELETE_BOOK:
					delete();
					break;
				case CASE_FIND_BOOK:
					find();
					break;
				case CASE_ARRANGER_BOOK:
					arranger();
					break;
				case CASE_EXPORT_FILE_BOOK:
					saveFile();
					break;
				case CASE_IMPORT_FILE_BOOK:
					readFile();
					break;
				case CASE_EXIT_PROGRAM:
					System.out.println("Good bye!");
					System.exit(0);
				default:
					break;
			}
		} while (!key.equals("q"));

		sc.close();

	}

	/**
	 * Hiển thị menu để chọn
	 * 
	 * @return void
	 */
	public static void displayMenu() {
		System.out.println("----------MENU MANAGE LIBRARY----------");
		System.out.println("(" + CASE_ADD_BOOK + ") Add book");
		System.out.println("(" + CASE_EDIT_BOOK + ") Edit book");
		System.out.println("(" + CASE_DELETE_BOOK + ") Delete book");
		System.out.println("(" + CASE_FIND_BOOK + ") Find book");
		System.out.println("(" + CASE_ARRANGER_BOOK + ") Arranger book");
		System.out.println("(" + CASE_EXPORT_FILE_BOOK + ") Export data to file");
		System.out.println("(" + CASE_IMPORT_FILE_BOOK + ") Import data from file and show it");
		System.out.println("(" + CASE_EXIT_PROGRAM + ") Quit");
	}

	/**
	 * Nhập số lượng muốn thêm
	 * 
	 * @return int
	 */
	public static int enterTheQuantity() {
		Scanner sc = new Scanner(System.in);
		try {
			int x;
			do {
				System.out.print("Enter the quantity : ");
				x = sc.nextInt();
			} while (x <= 0);
			return x;
		} catch (Exception e) {
			return -1;
		}

	}

	/**
	 * Thêm sách
	 * 
	 * @return void
	 */
	public static void create() {
		try {
			for (int i = 0; i < x; i++) {
				Scanner sc = new Scanner(System.in);
				System.out.println("Book #" + (i + 1));
				code[i] = getCode();
				System.out.print("Name : ");
				name[i] = sc.nextLine();
				insertYear(i);
				System.out.print("Author : ");
				author[i] = sc.nextLine();
				insertQuantity(i);
				insertPrice(i);
			}
		} catch (Exception e) {
			System.out.println(INPUT_ERROR + e.getMessage());
		}

	}

	/**
	 * Hiển thị header của table trong cmd
	 * 
	 * @return void
	 */
	public static void headTable() {
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("%-10s %-15s %-15s %-15s %-10s %-10s\n", "Code | ", "Name | ", "Release Year | ", " Author |",
				"Quantity | ", "Price");
		System.out.println("------------------------------------------------------------------------------------");
	}

	/**
	 * Hiển thị toàn bộ dữ liệu
	 * 
	 * @return void
	 */
	public static void show() {
		headTable();
		for (int i = 0; i < x; i++) {
			System.out.printf("%-10s %-15s %-15s %-15 %-10s %-10s\n", code[i], name[i], releaseYear[i], author[i],
					quantity[i], price[i]);
		}
	}

	/**
	 * Hiển thị 1 hàng dữ liệu
	 * 
	 * @param j
	 * @return void
	 */
	public static void showOne(int j) {
		for (int i = 0; i < x; i++) {
			if (i == j) {
				System.out.printf("%-10s %-15s %-15s %-15s %-10s %-10s\n", code[i], name[i], releaseYear[i], author[i],
						quantity[i], price[i]);
			}
		}
	}

	/**
	 * Chỉnh sửa sách theo mã sách
	 * 
	 * @return void
	 */
	public static void edit() {
		Scanner sc = new Scanner(System.in);
		try {
			if (x <= 0) {
				System.out.println(DATA_NOT_FOUND);
				return;
			}
			show();
			System.out.print("Enter code want edit : ");
			String c = sc.nextLine();
			for (int i = 0; i < x; i++) {
				if (c.equals("" + code[i])) {
					System.out.print("Enter new name : ");
					name[i] = sc.nextLine();
					System.out.println("Edit success");
					show();
					return;
				}
			}
			System.out.println(FIND_ERROR + c);
		} catch (Exception e) {
			System.out.println(INPUT_ERROR + e.getMessage());

		}
	}

	/**
	 * Xoá sách theo mã sách
	 * 
	 * @return void
	 */
	public static void delete() {
		Scanner sc = new Scanner(System.in);
		try {
			if (x <= 0) {
				System.out.println(DATA_NOT_FOUND);
				return;
			}
			show();
			System.out.print("Enter code want delete : ");
			int c = sc.nextInt();
			for (int i = 0; i < x; i++) {
				if (c == code[i]) {
					for (int j = i; j < x - 1; j++) {
						code[j] = code[j + 1];
						name[j] = name[j + 1];
						releaseYear[j] = releaseYear[j + 1];
						author[j] = author[j + 1];
						quantity[j] = quantity[j + 1];
						price[j] = price[j + 1];
					}
					x--;
					System.out.println("Delete success");
					show();
					return;
				}
			}
			System.out.println(FIND_ERROR + c);
		} catch (Exception e) {
			System.out.println(INPUT_ERROR + e.getMessage());
		}
	}

	/**
	 * Tìm sách theo tên
	 * 
	 * @return void
	 */
	public static void find() {
		Scanner sc = new Scanner(System.in);
		try {
			if (x <= 0) {
				System.out.println(DATA_NOT_FOUND);
				return;
			}
			show();
			System.out.print("Enter name want find : ");
			String n = sc.nextLine();
			int[] newBookArr = new int[x];
			int count = 0;
			for (int i = 0; i < x; i++) {
				if (n.equals(name[i])) {
					newBookArr[count] = i;
					count++;
				}
			}
			if (count == 0) {
				System.out.println("No find book with name : " + n);
				return;
			}
			headTable();
			for (int j = 0; j < count; j++) {
				showOne(newBookArr[j]);
			}

		} catch (Exception e) {
			System.out.println(INPUT_ERROR + e.getMessage());
		}
	}

	/**
	 * Sắp xếp tên theo giá
	 * 
	 * @return void
	 */
	public static void arranger() {
		if (x <= 0) {
			System.out.println(DATA_NOT_FOUND);
			return;
		}
		for (int i = 0; i < x - 1; i++) {
			for (int j = 0; j < x - i - 1; j++) {
				if (price[j] > price[j + 1]) {
					String tempName = name[j];
					name[j] = name[j + 1];
					name[j + 1] = tempName;

					int tempCode = code[j];
					code[j] = code[j + 1];
					code[j + 1] = tempCode;

					int tempReleaseYear = releaseYear[j];
					releaseYear[j] = releaseYear[j + 1];
					releaseYear[j + 1] = tempReleaseYear;

					String tempAuthor = author[j];
					author[j] = author[j + 1];
					author[j + 1] = tempAuthor;

					int tempQuantity = quantity[j];
					quantity[j] = quantity[j + 1];
					quantity[j + 1] = tempQuantity;

					int tempPrice = price[j];
					price[j] = price[j + 1];
					price[j + 1] = tempPrice;
				}
			}
		}
		System.out.println("Library after arranger");
		show();
	}

	/**
	 * Tạo mã sách ngẫu nhiên
	 * 
	 * @return int
	 */
	public static int getCode() {
		return (int) Math.floor(Math.random() * 100000);
	}

	/**
	 * Xử lí lưu data vào file
	 * 
	 * @throws IOException
	 * @return void
	 */
	public static void saveFile() throws IOException {
		try {
			if (x <= 0) {
				System.out.println(DATA_NOT_FOUND);
				return;
			}
			show();
			FileOutputStream file = new FileOutputStream(URI_SAVE_FILE);
			String data = "";
			for (int i = 0; i < x; i++) {
				data += code[i] + "," + name[i] + "," + releaseYear[i] + "," + author[i] + ","
						+ quantity[i] + "," + price[i] + "\n";
			}
			file.write(data.getBytes());
			file.close();
			System.out.println("Export data successfully");
		} catch (FileNotFoundException e) {
			System.out.println("Export data fail , error : " + e.getMessage());
		}
	}

	/**
	 * Xử lí xuất data từ file
	 * 
	 * @return void
	 */
	public static void readFile() {
		try {
			FileInputStream fileInputStream = new FileInputStream(URI_SAVE_FILE);

			Scanner scanner = new Scanner(fileInputStream);
			if (!scanner.hasNextLine()) {
				System.out.println(DATA_NOT_FOUND);
				scanner.close();
				return;
			}

			System.out.println("Import data successfully \n\n");

			headTable();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				System.out.printf("%-10s %-15s %-15s %-15s %-10s %-10s\n", data[0], data[1],
						data[2], data[3], data[4], data[5]);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Validate nhập năm phát hành
	 * 
	 * @param i
	 * @return void
	 */
	public static void insertYear(int i) {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Release year : ");
			releaseYear[i] = sc.nextInt();
		} while (releaseYear[i] < 1900 || releaseYear[i] > Year.now().getValue());
	}

	/**
	 * Validate nhập số lượng
	 * 
	 * @param i
	 * @return void
	 */
	public static void insertQuantity(int i) {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Quantity : ");
			quantity[i] = sc.nextInt();
		} while (quantity[i] <= 0);
	}

	/**
	 * Validate nhập giá
	 * 
	 * @param i
	 * @return void
	 */
	public static void insertPrice(int i) {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Price : ");
			price[i] = sc.nextInt();
		} while (price[i] <= 0);
	}
}
