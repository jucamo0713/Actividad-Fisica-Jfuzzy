package FIS;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.nio.file.Path;

public class FisProcessor {
    final static String fileName = Path.of("src", "main", "java", "FIS", "model.fcl").toString();
    private FIS fis;

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        this.fis.setVariable("temperature", temperature);
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
        this.fis.setVariable("humidity", humidity);
        fis.evaluate();
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
        this.fis.setVariable("windSpeed", windSpeed);
    }

    public void setUVIndex(int UVIndex) {
        this.UVIndex = UVIndex;
        this.fis.setVariable("UVIndex", UVIndex);
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        PrecipitationProbability = precipitationProbability;
        this.fis.setVariable("PrecipitationProbability", PrecipitationProbability);
    }

    private int temperature = -45;
    private int humidity = 0;
    private int windSpeed = 0;
    private int UVIndex = 1;
    private int PrecipitationProbability = 0;

    public FisProcessor() throws Exception {
        fis = FIS.load(fileName);
        if (fis == null) {
            System.err.println("No se puede cargar el archivo: '" + fileName + "'");
            throw new Exception("No se puede cargar el archivo: '" + fileName + "'");
        }
        this.fis.setVariable("temperature", temperature);
        this.fis.setVariable("humidity", humidity);
        this.fis.setVariable("windSpeed", windSpeed);
        this.fis.setVariable("UVIndex", UVIndex);
        this.fis.setVariable("PrecipitationProbability", PrecipitationProbability);
    }


    public Object[][] generateValues() {
        this.fis.evaluate();
        Object[][] response = new Object[2][3];
        Variable recommendedActivityLevel = fis.getVariable("RecommendedActivityLevel");
        response[0][0] = recommendedActivityLevel.getLatestDefuzzifiedValue();
        double lowPhysicalActivity = recommendedActivityLevel.getMembership("LowPhysicalActivity");
        double lightActivity = recommendedActivityLevel.getMembership("LightActivity");
        double moderateActivity = recommendedActivityLevel.getMembership("ModerateActivity");
        double highPerformanceActivity = recommendedActivityLevel.getMembership("HighPerformanceActivity");
        double peakPerformanceActivity = recommendedActivityLevel.getMembership("PeakPerformanceActivity");
        if (lowPhysicalActivity >= lightActivity &&
                lowPhysicalActivity >= moderateActivity &&
                lowPhysicalActivity >= highPerformanceActivity &&
                lowPhysicalActivity >= peakPerformanceActivity
        ) {
            response[1][0] = "Poca actividad física";
        } else if (lightActivity >= lowPhysicalActivity &&
                lightActivity >= moderateActivity &&
                lightActivity >= highPerformanceActivity &&
                lightActivity >= peakPerformanceActivity
        ) {
            response[1][0] = "Actividades ligeras";
        } else if (moderateActivity >= lowPhysicalActivity &&
                moderateActivity >= lightActivity &&
                moderateActivity >= highPerformanceActivity &&
                moderateActivity >= peakPerformanceActivity) {
            response[1][0] = "Actividades moderadas";
        } else if (highPerformanceActivity >= lowPhysicalActivity &&
                highPerformanceActivity >= lightActivity &&
                highPerformanceActivity >= moderateActivity &&
                highPerformanceActivity >= peakPerformanceActivity) {
            response[1][0] = "Actividades de alto rendimiento";
        } else {
            response[1][0] = "Actividades de máximo rendimiento";
        }
        Variable safetyIndex = fis.getVariable("SafetyIndex");
        response[0][1] = safetyIndex.getLatestDefuzzifiedValue();
        double dangerous = safetyIndex.getMembership("Dangerous");
        double risky = safetyIndex.getMembership("Risky");
        double safety = safetyIndex.getMembership("Safety");
        double recommended = safetyIndex.getMembership("Recommended");
        double veryRecommended = safetyIndex.getMembership("VeryRecommended");
        if (dangerous >= risky &&
                dangerous >= safety &&
                dangerous >= recommended &&
                dangerous >= veryRecommended
        ) {
            response[1][1] = "Peligroso";
        } else if (risky >= dangerous &&
                risky >= safety &&
                risky >= recommended &&
                risky >= veryRecommended
        ) {
            response[1][1] = "Riesgoso";
        } else if (safety >= dangerous &&
                safety >= risky &&
                safety >= recommended &&
                safety >= veryRecommended
        ) {
            response[1][1] = "Seguro";
        } else if (recommended >= dangerous &&
                recommended >= risky &&
                recommended >= safety &&
                recommended >= veryRecommended
        ) {
            response[1][1] = "Recomendado";
        } else {
            response[1][1] = "Muy Recomendado";
        }

        Variable qualityDayIndex = fis.getVariable("QualityDayIndex");
        response[0][2] = qualityDayIndex.getLatestDefuzzifiedValue();
        double poor = qualityDayIndex.getMembership("Poor");
        double fair = qualityDayIndex.getMembership("Fair");
        double good = qualityDayIndex.getMembership("Good");
        double veryGood = qualityDayIndex.getMembership("VeryGood");
        double excellent = qualityDayIndex.getMembership("Excellent");
        if (poor >= fair &&
                poor >= good &&
                poor >= veryGood &&
                poor >= excellent
        ) {
            response[1][2] = "Pobre";
        } else if (fair >= poor &&
                fair >= good &&
                fair >= veryGood &&
                fair >= excellent
        ) {
            response[1][2] = "Regular";
        } else if (good >= poor &&
                good >= fair &&
                good >= veryGood &&
                good >= excellent
        ) {
            response[1][2] = "Bueno";
        } else if (veryGood >= poor &&
                veryGood >= fair &&
                veryGood >= good &&
                veryGood >= excellent
        ) {
            response[1][2] = "Muy Bueno";
        } else {
            response[1][2] = "Excelente";
        }
        return response;
    }

    public void generateVarGraphics(){
        fis.evaluate();
        JFuzzyChart.get().chart(fis.getFunctionBlock("prop"));
    }
    public void generateResultGraphics(){
        fis.evaluate();
        Variable recommendedActivityLevel = fis.getVariable("RecommendedActivityLevel");
        JFuzzyChart.get().chart(recommendedActivityLevel,recommendedActivityLevel.getDefuzzifier(),true);
        Variable safetyIndex = fis.getVariable("SafetyIndex");
        JFuzzyChart.get().chart(safetyIndex,safetyIndex.getDefuzzifier(),true);
        Variable qualityDayIndex = fis.getVariable("QualityDayIndex");
        JFuzzyChart.get().chart(qualityDayIndex,qualityDayIndex.getDefuzzifier(),true);
    }
}
