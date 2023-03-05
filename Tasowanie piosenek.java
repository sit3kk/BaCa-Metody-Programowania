//Konrad Sitek - 4
import java.util.Scanner;


/*/
Dzialanie algorytmu polega na dzieleniu na cwiartki, pierwsza polowa to potega 2 natomiast druga to reszta
Rekurencyjne wywolania tworza coraz mniejsze az do osiagniecia roznicy indeksow 1 lub 0
 */

public class Source
{
    public static Scanner scan = new Scanner(System.in);
    public static String[] arr;


    public static String longestCommonPrefix(String x, String y) //Funkcja znajdujaca najdluzszy prefix wsrod dwoch wyrazow
    {
        String result = "";



        for(int i = 0, j = 0; i <= x.length()-1 && j <= y.length() - 1; i++,j++)
        {
            if(x.charAt(i) != y.charAt(j)) break; //Jezeli znaki nie sa sobie rowne przerwij petle

            result += x.charAt(i);
        }

        return result;

    }

    public static int findMaxPow(int a) //Funkcja znajdujaca najwieksza mozliwa potege 2 mieszczaca sie w a
    {
        int tmp = 1;

        while(tmp<=a) tmp*=2;

        return tmp;
    }

    public static String shuffle(String[] arr,int low, int high)
    {

        if(high - low <= 1) //Jezeli indeksy roznia sie o 1 lub sa sobie rowne zwroc najdluzszy prefix
        {
            return longestCommonPrefix(arr[low],arr[high]);
        }

        int end1,end2,s;

        int maxPow = findMaxPow((high-low)/2); // Szukanie najwiekszej mozliwej potegi 2 w polowie zbioru

        if(maxPow != high-low+1) //Jezeli potega nie jest rowna roznicy indeksow
        {
            s =  low + maxPow - 1;
            end1 = low + maxPow/2 - 1;
            end2 = s + ((high-s+1)/2);
        }
        else //Jezeli potega jest rowna roznicy indeksow
        {
            s = low + maxPow/2 - 1;
            end1 = low + maxPow/4 - 1;
            end2 = s + maxPow/4;
        }



        //Petla przesuwajaca komorki do czasu az osiagnie rozmiar podtablicy
        int nwd = nwd(end2 - s, end2 - end1), i = 0;
        while(i < nwd)
        {
            String tmp2 = arr[i + end1 + 1];
            int swap = i + end1 + 1;
            do{
                int swapNext = swap + end2 - s;
                if(swapNext > end2) swapNext = swapNext%end2 + end1;
                if (swapNext == i + end1 + 1) break;
                arr[swap] = arr[swapNext];
                swap = swapNext;
            }while(true);
            arr[swap] = tmp2;
            i++;

        }

        String str1 = shuffle(arr,low,s);
        String str2 = shuffle(arr,s+1,high);

        return longestCommonPrefix(str1,str2);

    }

    public static int nwd(int a, int b) //Obliczanie najwiekszego wspolnego dzielnika
    {

        while(a!=b)
        {
            if(a > b) a-=b;
            else b-=a;
        }
        return a;
    }


    public static void main(String[] args)
    {
        int z = Integer.parseInt(scan.nextLine());


        for(int i = 0 ; i < z; i++)
        {
            int songCount = Integer.parseInt(scan.nextLine());


            arr = scan.nextLine().split(" ");


            String result = shuffle(arr,0, arr.length-1);



            for(int j = 0 ; j < arr.length; j++)
            {
                System.out.print(arr[j] + " ");
            }

            System.out.println();
            System.out.println(result);
        }

    }

}
/*/


5
10
TrojkatBermudzki Azrael Lato HGH Swiatla Nasa Amex Yeah Triumf Megalomania
3
pl1 pl2 pl3
9
X x X x X x X x X
15
o o o o o o o y y y y y y y y
7
l L l L l L l


TrojkatBermudzki Nasa Azrael Amex Lato Yeah HGH Triumf Swiatla Megalomania

pl1 pl3 pl2
pl
X x x X X x x X X

o y o y o y o y o y o y o y y


l l L L l l L

 */