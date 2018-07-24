package com.example.rifat.parcelbarcodescanner;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rifat on 17-Feb-18.
 */


    public class Readfile {
   static List<String> list=new ArrayList<>();
   static List<String> list2=new ArrayList<>();
    public static BidiMap map1=new DualHashBidiMap() ;
    public static BidiMap map2=new DualHashBidiMap() ;
    public static BidiMap map3=new DualHashBidiMap() ;
    public static BidiMap Sylhet_Ward=new DualHashBidiMap() ;
    public static List<String> union=new ArrayList<>();
    public static List<String> district=new ArrayList<>();
    public static List<String> upazilla=new ArrayList<>();


  static  void read(List<String> l1,List<String> l2)
    {
        list = l1;
        list2 = l2;

/*        String x="",x2;
          try {
            System.out.println("Rifat B");
            FileReader file=new FileReader("E:\\study\\Java\\JA\\Parcel_Management\\src\\sample\\test.txt");

            BufferedReader br=new BufferedReader(file);
            System.out.println("Rifat 2");
            while((x2=br.readLine())!=null)
            {
                list.add(x2);

            }


        } catch (Exception e) {
        }
        try {
            FileReader wardfile=new FileReader("E:\\study\\Java\\JA\\Parcel_Management\\src\\sample\\Sylhet_City.txt");
            BufferedReader br=new BufferedReader(wardfile);
            while ((x2=br.readLine())!=null)
            {

                list2.add(x2);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        for(int i=0;i<list.size();i++)
        {
            String[] str=list.get(i).split(" ");
            if(str.length>=4)
            {
                for(int j=4;j<str.length;j++)
                {
                    str[3]=str[3]+" "+str[j];
                }
                //System.out.println(str.length+" "+str[0]+" "+str[1]+" "+str[2]+" "+str[3]);
                if(!map1.containsKey(str[1].toLowerCase()))
                {
                    district.add(str[1]);
                    int size=map1.size()+1;
                    String value;
                    if(size<10)
                    {
                        value="0"+Integer.toString(size);
                    }
                    else
                        value=Integer.toString(size);
                    map1.put(str[1].toLowerCase(),value);
//                       System.out.println("Rifat "+map1.get(str[1].toLowerCase())+" "+map1.getKey(value));
                }
                if(!map2.containsKey(str[2].toLowerCase()))
                {
                    upazilla.add(str[2]);
                    int size=map2.size()+1;
                    String value;
                    if(size<10)
                    {
                        value=map1.get(str[1].toLowerCase())+"-00"+Integer.toString(size);
                    }
                    else if(size<100)
                    {
                        value=map1.get(str[1].toLowerCase())+"-0"+Integer.toString(size);
                    }

                    else
                        value=map1.get(str[1].toLowerCase())+"-"+Integer.toString(size);

                    map2.put(str[2].toLowerCase(),value);
                   // System.out.println("Rifat "+map2.get(str[2].toLowerCase())+" "+map2.getKey(value));
                }
                if(!map3.containsKey(str[3].toLowerCase()))
                {
                    union.add(str[3]);
                    int size=map3.size()+1;
                    String value;
                    if(size<10)
                    {
                        value=map2.get(str[2].toLowerCase())+"-000"+Integer.toString(size);
                    }
                    else if(size<100)
                    {
                        value=map2.get(str[2].toLowerCase())+"-00"+Integer.toString(size);
                    }
                    else if(size<1000)
                    {
                        value=map2.get(str[2].toLowerCase())+"-0"+Integer.toString(size);
                    }
                    else
                        value=map2.get(str[2].toLowerCase())+"-"+Integer.toString(size);

                    map3.put(str[3].toLowerCase(),value);
                     System.out.println(map3.get(str[3].toLowerCase())+" "+map3.getKey(value));
                }

            }

        }
        for(int i=0;i<list2.size();i++)
        {
            String[] ward=list2.get(i).split(" ");
            String name="";
            for(int j=1;j<ward.length;j++)
            {
                name=name+" "+ward[j];
            }
            System.out.println(ward[0]+" "+name);
            Sylhet_Ward.put(name,ward[0]);
        }




    }
    static String getDistrict(String distCode)
    {
        return map1.getKey(distCode).toString();
    }
    static String getUpazilla(String UpaCode)
    {
        return map2.getKey(UpaCode).toString();

    }
    static String getUnion(String UnionCode)
    {
        return map3.getKey(UnionCode).toString();

    }
    static String getWard(String union)
    {
        return Sylhet_Ward.get(union).toString();
    }


    }


