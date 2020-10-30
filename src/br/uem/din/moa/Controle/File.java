package br.uem.din.moa.Controle;

import br.uem.din.moa.Model.City;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class File {

    public List<City> importCities(List<City> myCities){
        String pathArq = null;
        City actualCity = new City();

        JFileChooser file = new JFileChooser();
        file.setFileFilter(new FileNameExtensionFilter("Texto (*.txt)", "txt"));
        file.showDialog(file, "Abrir arquivo texto");

        try{
            pathArq = file.getSelectedFile().toString().trim();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado ou arquivo inválido.\n\nPrograma abortado!", "Falha ao abrir arquivo texto", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        //abrindo arquivo para leitura
        try (BufferedReader br = new BufferedReader(new FileReader(pathArq))){

            //obtendo dados da primeira linha do arquivo texto
            String completeLine = br.readLine().trim();
            String[] distances = completeLine.split(",");

            //setando dados da primeira linha
            for(int i = 0; i < distances.length; i++){
                actualCity.getDistancias().add(i, Integer.parseInt(distances[i].trim()));
            }
            myCities.add(actualCity);

            //variavel auxiliar para leitura das demais linhas
            int count = (distances.length - 1);

            while(count > 0){
                count--;
                actualCity = new City();

                //obtendo dados das demais linhas
                completeLine = br.readLine().trim();
                distances = completeLine.split(",");

                //setando dados das demais linhas
                for(int i = 0; i < distances.length; i++){
                    actualCity.getDistancias().add(i, Integer.parseInt(distances[i].trim()));
                }
                myCities.add(actualCity);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao ler arquivo!\nPrograma Abortado\nErro: ".concat(e.getMessage().trim()), "Falha ao ler arquivo", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return myCities;
    }
}
