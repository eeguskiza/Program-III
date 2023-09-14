package ClasesBasicas;
public class Resultado {
    private int año;
    private String torneo;
    private String ganador;
    private int rankingGanador;
    private String nacionalidadGanador;
    private String subcampeon;
    private int rankingSubcampeon;
    private String nacionalidadSubcampeon;
    private String resultadoFinal;


    public Resultado(int año, String torneo, String ganador, int rankingGanador, String nacionalidadGanador, String subcampeon, int rankingSubcampeon, String nacionalidadSubcampeon, String resultadoFinal) {
        this.año = año;
        this.torneo = torneo;
        this.ganador = ganador;
        this.rankingGanador = rankingGanador;
        this.nacionalidadGanador = nacionalidadGanador;
        this.subcampeon = subcampeon;
        this.rankingSubcampeon = rankingSubcampeon;
        this.nacionalidadSubcampeon = nacionalidadSubcampeon;
        this.resultadoFinal = resultadoFinal;
    }

    public String getTorneo() {
        return torneo;
    }

    public int getAño() {
        return año;
    }

    public String getGanador() {
        return ganador;
    }

    public String getSubcampeon() {
        return subcampeon;
    }

    public int getRankingGanador() {
        return rankingGanador;
    }

    public int getRankingSubcampeon() {
        return rankingSubcampeon;
    }

    public String getResultadoFinal() {
        return resultadoFinal;
    }

    public String getNacionalidadGanador() {
        return nacionalidadGanador;
    }

    public String getNacionalidadSubcampeon() {
        return nacionalidadSubcampeon;
    }

    public void setTorneo(String torneo) {
        this.torneo = torneo;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public void setSubcampeon(String subcampeon) {
        this.subcampeon = subcampeon;
    }

    public void setRankingGanador(int rankingGanador) {
        this.rankingGanador = rankingGanador;
    }

    public void setRankingSubcampeon(int rankingSubcampeon) {
        this.rankingSubcampeon = rankingSubcampeon;
    }

    public void setResultadoFinal(String resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    public void setNacionalidadGanador(String nacionalidadGanador) {
        this.nacionalidadGanador = nacionalidadGanador;
    }

    public void setNacionalidadSubcampeon(String nacionalidadSubcampeon) {
        this.nacionalidadSubcampeon = nacionalidadSubcampeon;
    }


    @Override
    public String toString() {
        return "Resultado{" +
                "año=" + año +
                ", torneo='" + torneo + '\'' +
                ", ganador='" + ganador + '\'' +
                ", subcampeon='" + subcampeon + '\'' +
                ", rankingGanador=" + rankingGanador +
                ", rankingSubcampeon=" + rankingSubcampeon +
                ", resultadoFinal='" + resultadoFinal + '\'' +
                '}';
    }


}
