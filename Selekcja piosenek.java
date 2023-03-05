Konrad Sitek - 4

import java.util.Scanner;


Algorytm median posiadajacy w przypadku pesymistycznym zlozonsc liniowa
Polegajacy sortowanie 5 elementowych podzbiorow, rekurencyjnie znajdujacy zadana liczbe
Poprzez oblicznaie kolejnych median
 


public class Source {

    public static Scanner scan = new Scanner(System.in);

    public static void insertionSort(int[] arr,int l, int r) Sortowanie kolejnych piatek
    {
        for(int i = l + 1; i  r ; ++i)
        {
            int key = arr[i];
            int j = i - 1;

            while(j = l && arr[j]  key)
            {
                arr[j+1] = arr[j];
                j = j - 1;
            }
            arr[j+1] = key;
        }
    }


    public static int select(int[] arr, int l, int r, int k)
    {
        if(r - l = 1) return arr[l];

        int x = 0;
        for(int i = l ; i  r ; i +=5)
        {
            int endIndex;
            if(i + 5  r) endIndex = i + 5; Wyznacznie skrajnego indeksu
            else endIndex = r;
            insertionSort(arr,i,endIndex); Sortowanie grupy 5 elementow

             Mediana przesuwana na poczatek tablicy
            int tmp = arr[l+x];
            arr[l+x] = arr[(i+endIndex)2];
            arr[(i+endIndex)2] = tmp;
            x++; Licznik median

        }


        Obliczanie kolejnej mediany
        int pivot = select(arr,l,l+x,(x+1)2);

        int tmpl = l;
        int tmpr = r - 1 ;


        Maksymalnie wykona sie n razy

        for(int i = tmpl; i = tmpr;) {
            if (arr[i]  pivot)
            {
                int tmp = arr[tmpl];
                arr[tmpl] = arr[i];
                arr[i] = tmp;
                tmpl++;
                i++;
            }
            else if(arr[i] == pivot) i++;
            else
            {
                int tmp = arr[i];
                arr[i] = arr[tmpr];
                arr[tmpr] = tmp;
                tmpr--;
            }
        }

        Szukanie brzegowych indexow mediany
        int i = l;
        while(arr[i]  pivot) ++i;
        int tmp = i - 1;
        while(i  r && arr[i] == pivot) ++i;
        int tmp2 = i - 1;


        if(k = tmp - l) return select(arr,l,tmp+1,k);
        else if(k = tmp2 - l) return pivot;
        else return select(arr,tmp2+1,r,k + l - (tmp2+1));

    }

    public static void main(String[] args)
    {

        int z = scan.nextInt();

        for(int set = 0 ; set  z ; set++)
        {
            int n = scan.nextInt();
            int[] arr = new int[n];

            for(int i = 0 ; i  n ; i++) arr[i] = scan.nextInt();


            int m = scan.nextInt();
            for(int i = 0; i  m ; i++)
            {
                int k = scan.nextInt();
                if(k  0 && arr.length=k)
                {
                    System.out.println(k +   + select(arr,0,arr.length,k-1));
                }
                else System.out.println(k +  brak);
            }

        }

    }

}



3
9
5 2 1 3 4 8 9 10 21
4
4 5 7 1
1
1
1
5
10
9 1 2 3 4 5 9 0 543 76
6
10 9 8 7 6 5 4 3 2 1


4 10
5 10
7 10
1 10
5 brak
10 543
9 543
8 9
7 9
6 9
5 9
 