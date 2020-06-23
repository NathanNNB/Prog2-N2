package br.mack.dao;

import br.mack.api.Result;
import br.mack.api.Trends;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrendsDao {

    Trends dataBase;

    public TrendsDao() {
        this.dataBase = new Trends();
        readFile();
    }

    //CREATE
    public void createResult(Result result){
        this.dataBase.getResults().add(result);
        writeFile();
    }

    //READ
    public Trends getAllTrends() {
        return this.dataBase;
    }

    //UPDATE
    public void updateResult(Result result){
        for(int i=0; i<this.dataBase.getResults().size(); i++){
            if(this.dataBase.getResults().get(i).getDate().equals(result.getDate())){
               this.dataBase.getResults().set(i, result);
            }
        }
        writeFile();
    }

    //DELETE
    public void deleteResult(Result result){
        for(int i=0; i<this.dataBase.getResults().size(); i++){
            if(this.dataBase.getResults().get(i).getDate().equals(result.getDate())){
                this.dataBase.getResults().remove(i);
            }
        }
        writeFile();
    }

    //Funções auxiliares

    //Le o arquivo csv e instancia a vriavel database
    public void readFile(){
        System.out.println("TrendsDAO - Lendo dados do arquivo CSV");

        try (Scanner scanner = new Scanner(new File("./resources/batman.csv"));) {

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] cols = line.split(",");
                Date week = null; // usado para testar se a linha começa com uma data

                try {
                    week = sdf.parse(cols[0]);
                } catch(Exception e) { /*Não é uma data*/ }

                // Lendo dados
                if (week != null) {
                    Result result = new Result();
                    result.setDate(cols[0]);
                    result.setValue(Double.parseDouble(cols[1]));
                    this.dataBase.getResults().add(result);

                    // Lendo cabecalho
                }  else if(cols.length > 0 && (cols[0].equals("Time") || cols[0].equals("Hora"))) {
                    this.dataBase.setTerm(cols[1]);
                }
            }

            System.out.println("TrendsDAO - Leitura realizada");
        } catch (Exception ex) {
            this.dataBase = new Trends();
            ex.printStackTrace();
            System.out.println("TrendsDAO - Erro na leitura do CSV");
        }
    }


    public void writeFile(){
        System.out.println("TrendsDAO - Escrevendo no arquivo CSV");

        String str = "Categoria: Todas as categorias\n\nSemana,";
        str += this.dataBase.getTerm() + "\n";

        List<Result> results = this.dataBase.getResults();
        for (int i=0; i<this.dataBase.getResults().size(); i++){
            Result r = results.get(i);
            str += r.getDate() + "," + r.getValue();
        }

        try {
            FileWriter writer = new FileWriter("./resources/batman.csv");
            writer.write(str);
            writer.close();
            System.out.println("TrendsDAO - Dados escritos com sucesso!");
        } catch (IOException e) {
            System.out.println("TrendsDAO - Erro na escrita do CSV");
            e.printStackTrace();
        }
    }

}
