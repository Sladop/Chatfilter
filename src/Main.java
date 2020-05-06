import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import filter.Chatfilter;
import filter.FilterType;

public class Main {

	/**
	 * This is the Main method to do tests.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		float mute = 85;
		float support = 60;
		FilterType type;
		String blackString = "Hurensohn,Huan,Wichser,Penner,Fotze,Ficker,Hure,Nutte,"
				+ "Nuttensohn,Hurentochter,Nuttentochter,Loser,Noob,Kiddi,Spasst,"
				+ "Spast,Spassti,Idiot,Vollidiot,Lappen,Opfer,Trottel,Fickfresse,"
				+ "Maul,ez,Arsch,Arschloch,Arschficker,Arschfick,"
				+ "Schwanzlutscher,Cocklutscher,Lutscher,Huso,Mistgeburt,Lusche,"
				+ "Dumbass,Sackgesicht,Schwuchtel,Fettsack,ficken,fick,Bastard,"
				+ "Scheisse,shit,Officialhurensohn,Drecksack,Wixxer,"
				+ "Wixer,Pisser,Scheissfresse,ezz,e2,milf,pussy,titten,schwanz,"
				+ "sperma,möse,porn,pornhub,porno,Hohlkopf,oral,anal,Schwanzfresse,"
				+ "huren,fkk,Hitler,Osama,Schweinefresse,Hundesohn,"
				+ "Judensohn,Nazi,Bitch,Krüppel,affenficker,heilhitler,nigga,nigger,Pimmel,Muschi,nuttenficker,schnauze";

		List<String> blacklist = Arrays.asList(blackString.toLowerCase().split(","));
		List<String> insignificants = Arrays.asList(".", "*");

		Chatfilter filter = new Chatfilter(blacklist, insignificants);

		File file = new File("C:/Users/phili/Downloads/eclipse/Chatfilter/src/Sladop.txt");
		File file = new File("C:/Users/phili/Downloads/eclipse/Chatfilter/src/Blacklist.txt");

		BufferedReader in = null;
		

		try {
			in = new BufferedReader(new FileReader("C:/Users/phili/Downloads/eclipse/Chatfilter/src/Sladop.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		int counter = 0;
		String out = "Parameter:    Mute: " + mute + " Support: " + support + "\n"
				+ "__________________________________________\n\n";
		try {
			while ((line = in.readLine()) != null && counter < 200) {
				counter++;
				type = filter.checkInput(line, mute, support);
				out += "Gelesene Zeile:              " + line + "\nMatch-Aktion:               " + type
						+ "\nProzent:                          " + type.getMatchPercent()
						+ " \nMatch-Wort:                  " + type.getMatchString() + "\n\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeLine(out);

	}

	private static void writeLine(String line) {
		File newFile = new File("ReadOut.txt");
		try {
			FileWriter out = new FileWriter("C:/Users/phili/Downloads/eclipse/Chatfilter/src/ReadOut.txt");
			out.write(line);
			out.close();
		} catch (IOException e) {
			System.out.println("fehler");
		}
	}
}
