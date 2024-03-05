package GUI;

import FIS.FisProcessor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Form extends JFrame{
    private JPanel general;
    private JTabbedPane tabbedPane1;
    private JLabel temperaturaText;
    private JSlider temperatura;
    private JSlider humedad;
    private JLabel humedadText;
    private JSlider velocidadViento;
    private JLabel velocidadVientoTexto;
    private JSlider indiceUV;
    private JLabel indiceUVText;
    private JSlider probPrecipitacion;
    private JLabel probPrecipitacionText;
    private JLabel recommendedActivityLevelValue;
    private JLabel recommendedActivityLevelSuggest;
    private JLabel safetyIndexValue;
    private JLabel safetyIndexSuggestion;
    private JLabel qualityDayIndexValue;
    private JLabel qualityDayIndexSuggestion;
    private JButton generarGraficosDeVariablesButton;
    private JButton generarGrafiocsDeResultadosButton;

    private FisProcessor fis;
    public Form() throws Exception {
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.general.revalidate();
        this.setContentPane(this.general);
        this.setLocationRelativeTo(null);
        this.pack();
        this.fis = new FisProcessor();
        this.temperatura.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                temperaturaText.setText(String.valueOf(temperatura.getValue()));
                fis.setTemperature(temperatura.getValue());
            }
        });
        this.humedad.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                humedadText.setText(String.valueOf(humedad.getValue()));
                fis.setHumidity(humedad.getValue());
            }
        });
        this.velocidadViento.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                velocidadVientoTexto.setText(String.valueOf(velocidadViento.getValue()));
                fis.setWindSpeed(velocidadViento.getValue());
            }
        });
        indiceUV.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                indiceUVText.setText(String.valueOf(indiceUV.getValue()));
                fis.setUVIndex(indiceUV.getValue());
            }
        });
        probPrecipitacion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                probPrecipitacionText.setText(String.valueOf(probPrecipitacion.getValue()));
                fis.setPrecipitationProbability(probPrecipitacion.getValue());
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane1.getSelectedIndex() ==1){
                    Object[][] data = fis.generateValues();
                    recommendedActivityLevelValue.setText(data[0][0].toString());
                    recommendedActivityLevelSuggest.setText(data[1][0].toString());
                    safetyIndexValue.setText(data[0][1].toString());
                    safetyIndexSuggestion.setText(data[1][1].toString());
                    qualityDayIndexValue.setText(data[0][2].toString());
                    qualityDayIndexSuggestion.setText(data[1][2].toString());
                    pack();
                }
            }
        });
        generarGraficosDeVariablesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fis.generateVarGraphics();
                super.mouseClicked(e);
            }
        });
        generarGrafiocsDeResultadosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fis.generateResultGraphics();
                super.mouseClicked(e);
            }
        });
    }
}
