package org.vaadin.example.models;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;

@JsonPropertyOrder({ "_id", "from", "to", "timeRange", "total" })
public class TurismoComunidad {
    private String _id;
    private From from;
    private To to;
    private TimeRange timeRange;
    private Integer total;

    public TurismoComunidad() {
        this._id = UUID.randomUUID().toString();
    }

    // Getters and Setters

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    // Inner classes for From, To, and TimeRange

    public static class From {
        private String comunidad;
        private String provincia;

        // Getters and Setters

        public String getComunidad() {
            return comunidad;
        }

        public void setComunidad(String comunidad) {
            this.comunidad = comunidad;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }
    }

    public static class To {
        private String comunidad;
        private String provincia;

        // Getters and Setters

        public String getComunidad() {
            return comunidad;
        }

        public void setComunidad(String comunidad) {
            this.comunidad = comunidad;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }
    }

    public static class TimeRange {
        private String fecha_inicio;
        private String fecha_fin;
        private String period;

        // Getters and Setters

        public String getFechaInicio() {
            return fecha_inicio;
        }

        public void setFechaInicio(String fecha_inicio) {
            this.fecha_inicio = fecha_inicio;
        }

        public String getFechaFin() {
            return fecha_fin;
        }

        public void setFechaFin(String fecha_fin) {
            this.fecha_fin = fecha_fin;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }
    }
}
