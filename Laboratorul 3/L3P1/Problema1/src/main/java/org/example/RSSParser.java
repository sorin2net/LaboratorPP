package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

// ADT pentru reprezentarea unui item RSS
class RSSItem{
    String title;
    String link;
    String description;
    String pubDate;

    public RSSItem(String title, String link, String description, String pubDate){
        this.title=title;
        this.link=link;
        this.description=description;
        this.pubDate=pubDate;
    }

    @Override
    public String toString(){
        return "Titlu: "+title+"\nLink: "+link+"\nData publicarii: "+pubDate+"\n";
    }
}

// ADT pentru canalul RSS
class RSSFeed{
    String title;
    String link;
    String description;
    List<RSSItem> items; //stocam colectie de obiecte de tip RSSItem

    public RSSFeed(String title, String link, String description){
        this.title=title;
        this.link=link;
        this.description=description;
        this.items=new ArrayList<>();
    }

    public void addItem(RSSItem item){
        items.add(item);
    }

    public void printFeed(){
        System.out.println("Flux RSS: "+title);
        System.out.println("Link: "+link);
        System.out.println("Descriere: "+description+"\n");

        for(RSSItem item:items){
            System.out.println(item);
        }
    }

    public void writeToFile(String filename){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(filename))){
            writer.write("Flux RSS: "+title+"\n");
            writer.write("Link: "+link+"\n");
            writer.write("Descriere: "+description+"\n\n");

            for(RSSItem item:items){
                writer.write(item.toString()+"\n");
            }
            System.out.println("Datele au fost salvate in fisierul: "+filename);
        } catch(IOException e){
            System.err.println("Eroare la scrierea in fisier: "+e.getMessage());
        }
    }
}

public class RSSParser{
    public static void main(String[] args){
        String url="https://www.gandul.ro/rss";

        try{
            Document doc=Jsoup.connect(url).get(); //realizam o conexiune la un site folosind Jsoup si preia continutul acestuia in Document
            Element channel=doc.selectFirst("channel");

            String feedTitle=channel.selectFirst("title").text();
            String feedLink=channel.selectFirst("link").text();
            String feedDescription=channel.selectFirst("description").text();

            RSSFeed feed=new RSSFeed(feedTitle,feedLink,feedDescription);

            Elements items=channel.select("item");
            for(Element item:items){
                String itemTitle=item.selectFirst("title").text();
                String itemLink=item.selectFirst("link").text();
                String itemDescription=item.selectFirst("description").text();

                //Preluare si verificare data de publicare
                Element pubDateElement=item.select("pubDate").first();
                String itemPubDate="Data necunoscuta";

                if(pubDateElement!=null){
                    itemPubDate=pubDateElement.text().trim();
                    if(itemPubDate.isEmpty()){
                        itemPubDate="Data necunoscuta"; // Folosim un fallback daca data este goala
                    }
                }

                feed.addItem(new RSSItem(itemTitle,itemLink,itemDescription,itemPubDate));
            }

            feed.printFeed();
            feed.writeToFile("Continut.txt");
        } catch(IOException e){
            System.err.println("Eroare la preluarea sau procesarea fluxului RSS: "+e.getMessage());
        }
    }
}
