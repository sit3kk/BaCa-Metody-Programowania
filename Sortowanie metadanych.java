//Konrad Sitek - 4

import java.util.Scanner;


/*/

QuickSort polaczony z selection sortem dla podzadan mniejszych lub rowoych 5 sortujacy rekordy przez wybrana kolumne
Wersja iteratcyjna bez stosu oznacza elementy dodajac znak # na poczatku, nastepnie funkcja findNext znadjduje ten indeks
 i przywraca napis do wersji pierwotnej

 */


public class Source {


    public static boolean isDigit(String word) //Funkcja sprawdzajaca czy dane slowo jest liczba
    {
        for(int i = 0 ; i < word.length(); i++) if(!Character.isDigit(word.charAt(i))) return false;

        return true;
    }

    public static void selectionSort(String[][] arr, int begin, int end,int column, int mode)
    {

        boolean digitMode = isDigit(arr[0][column]); //Sprawdzanie jaki rodzaj danych

        for (int i = begin; i < end - 1; i++) {

            int min = i; //Szukanie najmenijeszego elementu
            for (int j = i + 1; j < end; j++) {

                boolean compare = false;

                compare = compare(arr[j][column],arr[min][column],digitMode,mode); //Sprawdzenie czy zachodzi warunek str1<str2 lub jego negacja

                if(compare) min = j;

            }

            //Zamiana elementow
            swap(arr,i,min);
        }
    }

    public static String makeNegative(String str) //Oznaczenie komorki jako ujemna
    {
        if(str.charAt(0) == '#') return str.substring(1,str.length());
        else return '#' + str;

    }
    public static boolean compare(String str1 , String str2, boolean digitMode, int mode) //str1 < str2
    {
        //Jezeli dane sa liczbami
        if(digitMode) {

            if (mode == 1) //Tryb sortowania rosnacego
            {
                if (str1.charAt(0) != '#' && str2.charAt(0) != '#') return Integer.parseInt(str1) < Integer.parseInt(str2);

                 else if (str1.charAt(0) == '#' && str2.charAt(0) == '#')
                     return Integer.parseInt(str1.substring(1,str1.length())) < Integer.parseInt(str2.substring(1,str2.length())); // str1<str2

                 else if (str1.charAt(0) == '#' && str2.charAt(0) != '#')
                     return true; //str1<str2

                else return false;

            }
            else //Tryb sortowania malejacego
            {
                if (str1.charAt(0) != '#' && str2.charAt(0) != '#') return !(Integer.parseInt(str1) < Integer.parseInt(str2));

                else if (str1.charAt(0) == '#' && str2.charAt(0) == '#')
                    return !(Integer.parseInt(str1.substring(1,str1.length())) < Integer.parseInt(str2.substring(1,str2.length()))); // str1<str2

                else if (str1.charAt(0) == '#' && str2.charAt(0) != '#')
                    return false; //str2>str1

                return true;


            }
        }
        else
        {
            if(mode == 1) //Tryb sortowania rosnacego
            {
                if (str1.charAt(0) != '#' && str2.charAt(0) != '#') return str1.compareTo(str2) < 0; //str1<str2

                 else if (str1.charAt(0) == '#' && str2.charAt(0) == '#') return str1.compareTo(str2) < 0; // str1<str2

                 else if (str1.charAt(0) == '#' && str2.charAt(0) != '#') return true; //str1<str2

                 return false;

            }
            else //Tryb sortowania malejacego
            {
                if (str1.charAt(0) != '#' && str2.charAt(0) != '#') return !(str1.compareTo(str2) < 0); //str1>str2

                 else if (str1.charAt(0) == '#' && str2.charAt(0) == '#') return !(str1.compareTo(str2) < 0); // str1>str2

                 else if (str1.charAt(0) == '#' && str2.charAt(0) != '#') return false;

                 return true;


            }

        }

    }

    public static void swap(String[][] arr, int i, int j) {
        String[] tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int findNext(String[][] arr, int l, int size, int column) //Funkcja znajdujaca nastepna oznaczona liczbe
    {

        for(int i = l; i < size ; ++i) if(arr[i][column].charAt(0) == '#') return i;

        return size - 1;
    }

    public static int partition(String[][] arr, int l, int r, int column, int mode)
    {

        boolean digitMode = isDigit(arr[0][column]); //Sprawdzenie czy komorka jest liczba


        String pivot = arr[(l+r)/2][column]; //Wyznaczenie srodkowego pivota

        while(l <= r)
        {
            while(compare(pivot,arr[r][column], digitMode,mode)) r--;

            while(compare(arr[l][column],pivot, digitMode,mode)) l++;

            if(l <= r)
            {
                swap(arr,l,r); //Zamiana indeksow
                l++;
                r--;
            }
        }

        return l;
    }


    public static void quickSort(String[][] arr, int size, int column, int mode) {

        int l = 0,r = size - 1;
        int q, i = 0;
        int tmpr = r;

        do
        {

            if(tmpr-l<=5 && i == 0)
            {
                selectionSort(arr,l,tmpr,column,mode); //Selection sort dla podzadan mniejszych niz 5
            }

            i--;
            while(l < tmpr)
            {
                q = partition(arr,l,tmpr,column,mode);
                arr[tmpr][column] = makeNegative(arr[tmpr][column]); //Oznaczenie liczby
                tmpr = q - 1;
                ++i;
            }
            if(i < 0) break;

            l++;
            tmpr = findNext(arr,l,size,column); //Nastepna oznaczona komorka
            arr[tmpr][column] = makeNegative(arr[tmpr][column]); //Odwrocenie negacji

        }while(true);
    }

    public static void main(String[] args)
    {

        Scanner scan = new Scanner(System.in);
        int n = Integer.parseInt(scan.nextLine());


        while(n-- > 0)
        {
            String[] nums = scan.nextLine().split(",");

            String[] format = scan.nextLine().split(",");

            String[][] songs = new String[Integer.parseInt(nums[0])][format.length];

            for(int i = 0 ; i < Integer.parseInt(nums[0]); i++) songs[i] = scan.nextLine().split(",");

            quickSort(songs,Integer.parseInt(nums[0]) ,Integer.parseInt(nums[1])-1,1);


            System.out.print(format[Integer.parseInt(nums[1])-1]);
            for(int i = 0 ; i < format.length ; i++) if(i != Integer.parseInt(nums[1])-1) System.out.print(","+format[i]);
            System.out.println();

            if(Integer.parseInt(nums[2]) == -1) for(int i = 0; i < songs.length/2 ; i++) swap(songs,i,songs.length - 1 - i);

            for(int i = 0 ; i < songs.length ; i++)
            {
                System.out.print(songs[i][Integer.parseInt(nums[1])-1]);
                for(int j = 0 ; j < format.length ; j++) if(j != Integer.parseInt(nums[1])-1)System.out.print(","+songs[i][j]);
                System.out.println();
            }
            System.out.println();
        }

    }

}


/*/
3
20,2,-1
Album,Year,Songs,Length
Stadium Arcadium,1,28,122
Unlimited Love,2,17,73
Californication,3,15,56
Stadium Arcadium,4,28,122
Unlimited Love,5,17,73
Californication,6,15,56
Stadium Arcadium,7,28,122
Unlimited Love,8,17,73
Californication,9,15,56
Stadium Arcadium,10,28,122
Unlimited Love,11,17,73
Californication,12,15,56
Stadium Arcadium,13,28,122
Unlimited Love,14,17,73
Californication,15,15,56
Stadium Arcadium,16,28,122
Unlimited Love,17,17,73
Californication,18,15,56
Unlimited Love,19,17,73
Californication,20,15,56


Output
Year,Album,Songs,Length
20,Californication,15,56
19,Unlimited Love,17,73
18,Californication,15,56
17,Unlimited Love,17,73
16,Stadium Arcadium,28,122
15,Californication,15,56
14,Unlimited Love,17,73
13,Stadium Arcadium,28,122
12,Californication,15,56
11,Unlimited Love,17,73
10,Stadium Arcadium,28,122
9,Californication,15,56
8,Unlimited Love,17,73
7,Stadium Arcadium,28,122
6,Californication,15,56
5,Unlimited Love,17,73
4,Stadium Arcadium,28,122
3,Californication,15,56
2,Unlimited Love,17,73
1,Stadium Arcadium,28,122

/*/