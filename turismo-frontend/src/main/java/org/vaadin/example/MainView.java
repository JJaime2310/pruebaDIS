package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.models.TurismoComunidad;
import org.vaadin.example.services.TurismoComunidadService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<TurismoComunidad> turismoGrid = new Grid<>(TurismoComunidad.class, false);
    private Grid<TurismoComunidad> agrupadasGrid = new Grid<>(TurismoComunidad.class, false);
    private TurismoComunidadService turismoComunidadService;
    private List<TurismoComunidad> turismoList;

    @Autowired
    public MainView(TurismoComunidadService turismoComunidadService) {
        this.turismoComunidadService = turismoComunidadService;

        // Pestañas
        Tab turismoTab = new Tab("Turismo");
        Tab comunidadesTab = new Tab("Comunidades Agrupadas");
        Tabs tabs = new Tabs(turismoTab, comunidadesTab);

        // Contenedores para cada pestaña
        VerticalLayout turismoLayout = crearTurismoLayout();
        VerticalLayout comunidadesLayout = crearComunidadesLayout();

        // Manejador de cambio de pestaña
        turismoLayout.setVisible(true);
        comunidadesLayout.setVisible(false);

        tabs.addSelectedChangeListener(event -> {
            turismoLayout.setVisible(event.getSelectedTab() == turismoTab);
            comunidadesLayout.setVisible(event.getSelectedTab() == comunidadesTab);
        });

        // Contenedor principal
        VerticalLayout content = new VerticalLayout(turismoLayout, comunidadesLayout);
        content.setSizeFull();
        content.setPadding(false);
        content.setSpacing(false);

        // Añadir al layout principal
        add(tabs, content);
        setSizeFull(); // Expandir el tamaño del layout principal
    }

    private VerticalLayout crearTurismoLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        // Botón "Nuevo"
        Button nuevoButton = new Button("Nuevo", event -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Nuevo TurismoComunidad");

            FormLayout formLayout = new FormLayout();
            TextField fromComunidadField = new TextField("From Comunidad");
            TextField fromProvinciaField = new TextField("From Provincia");
            TextField toComunidadField = new TextField("To Comunidad");
            TextField toProvinciaField = new TextField("To Provincia");
            TextField fechaInicioField = new TextField("Fecha Inicio");
            TextField fechaFinField = new TextField("Fecha Fin");
            TextField periodField = new TextField("Periodo");
            TextField totalField = new TextField("Total");

            // Validaciones
            fromComunidadField.setPattern("[a-zA-Z\\s]+");
            fromComunidadField.setErrorMessage("Solo se permiten letras");
            fromProvinciaField.setPattern("[a-zA-Z\\s]+");
            fromProvinciaField.setErrorMessage("Solo se permiten letras");
            toComunidadField.setPattern("[a-zA-Z\\s]+");
            toComunidadField.setErrorMessage("Solo se permiten letras");
            toProvinciaField.setPattern("[a-zA-Z\\s]+");
            toProvinciaField.setErrorMessage("Solo se permiten letras");
            fechaInicioField.setPattern("\\d{4}-\\d{2}-\\d{2}");
            fechaInicioField.setErrorMessage("Formato: AAAA-MM-DD");
            fechaFinField.setPattern("\\d{4}-\\d{2}-\\d{2}");
            fechaFinField.setErrorMessage("Formato: AAAA-MM-DD");
            totalField.setPattern("\\d+");
            totalField.setErrorMessage("Solo se permiten números");

            Button saveButton = new Button("Guardar");
            saveButton.setEnabled(false); // Deshabilitado inicialmente

            // Listener para habilitar/deshabilitar el botón "Guardar"
            fromComunidadField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));
            fromProvinciaField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));
            toComunidadField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));
            toProvinciaField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));
            fechaInicioField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));
            fechaFinField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));
            totalField.addValueChangeListener(e -> validateForm(saveButton, fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField, fechaInicioField, fechaFinField, totalField));

            saveButton.addClickListener(saveEvent -> {
                try {
                    TurismoComunidad newTurismo = new TurismoComunidad();
                    newTurismo.setId(UUID.randomUUID().toString());
                    newTurismo.setFrom(new TurismoComunidad.From());
                    newTurismo.getFrom().setComunidad(fromComunidadField.getValue());
                    newTurismo.getFrom().setProvincia(fromProvinciaField.getValue());
                    newTurismo.setTo(new TurismoComunidad.To());
                    newTurismo.getTo().setComunidad(toComunidadField.getValue());
                    newTurismo.getTo().setProvincia(toProvinciaField.getValue());
                    newTurismo.setTimeRange(new TurismoComunidad.TimeRange());
                    newTurismo.getTimeRange().setFechaInicio(fechaInicioField.getValue());
                    newTurismo.getTimeRange().setFechaFin(fechaFinField.getValue());
                    newTurismo.getTimeRange().setPeriod(periodField.getValue());
                    newTurismo.setTotal(Integer.parseInt(totalField.getValue()));

                    // Enviar al backend
                    turismoComunidadService.addTurismoComunidad(newTurismo);


                    Notification.show("Nuevo TurismoComunidad añadido");
                    dialog.close();

                    // Refrescar la página
                    getUI().ifPresent(ui -> ui.getPage().reload());
                } catch (Exception e) {
                    Notification.show("Error al guardar: " + e.getMessage());
                }
            });

            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

            formLayout.add(fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField,
                    fechaInicioField, fechaFinField, periodField, totalField);
            dialog.add(formLayout, saveButton, cancelButton);

            dialog.open();
        });
        turismoGrid.addItemDoubleClickListener(event -> {
            TurismoComunidad selectedTurismo = event.getItem();
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Actualizar TurismoComunidad");

            FormLayout formLayout = new FormLayout();
            TextField fromComunidadField = new TextField("From Comunidad", selectedTurismo.getFrom().getComunidad());
            TextField fromProvinciaField = new TextField("From Provincia", selectedTurismo.getFrom().getProvincia());
            TextField toComunidadField = new TextField("To Comunidad", selectedTurismo.getTo().getComunidad());
            TextField toProvinciaField = new TextField("To Provincia", selectedTurismo.getTo().getProvincia());
            TextField fechaInicioField = new TextField("Fecha Inicio", selectedTurismo.getTimeRange().getFechaInicio());
            TextField fechaFinField = new TextField("Fecha Fin", selectedTurismo.getTimeRange().getFechaFin());
            TextField periodField = new TextField("Periodo", selectedTurismo.getTimeRange().getPeriod());
            TextField totalField = new TextField("Total", String.valueOf(selectedTurismo.getTotal()));

            // Botones de acción
            Button updateButton = new Button("Actualizar", clickEvent -> {
                try {
                    // Actualizar los datos del objeto seleccionado solo si los campos no están vacíos
                    if (!fromComunidadField.isEmpty()) {
                        selectedTurismo.getFrom().setComunidad(fromComunidadField.getValue());
                    }
                    if (!fromProvinciaField.isEmpty()) {
                        selectedTurismo.getFrom().setProvincia(fromProvinciaField.getValue());
                    }
                    if (!toComunidadField.isEmpty()) {
                        selectedTurismo.getTo().setComunidad(toComunidadField.getValue());
                    }
                    if (!toProvinciaField.isEmpty()) {
                        selectedTurismo.getTo().setProvincia(toProvinciaField.getValue());
                    }
                    if (!fechaInicioField.isEmpty()) {
                        selectedTurismo.getTimeRange().setFechaInicio(fechaInicioField.getValue());
                    }
                    if (!fechaFinField.isEmpty()) {
                        selectedTurismo.getTimeRange().setFechaFin(fechaFinField.getValue());
                    }
                    if (!periodField.isEmpty()) {
                        selectedTurismo.getTimeRange().setPeriod(periodField.getValue());
                    }
                    if (!totalField.isEmpty()) {
                        try {
                            selectedTurismo.setTotal(Integer.parseInt(totalField.getValue()));
                        } catch (NumberFormatException e) {
                            Notification.show("El campo 'Total' debe ser un número válido.");
                            return;
                        }
                    }

                    // Enviar datos actualizados al backend
                    turismoComunidadService.updateTurismoComunidad(selectedTurismo);

                    Notification.show("TurismoComunidad actualizado correctamente");
                    dialog.close();

                    // Actualizar el grid con los nuevos datos
                    turismoGrid.getDataProvider().refreshItem(selectedTurismo);
                } catch (Exception ex) {
                    Notification.show("Error al actualizar TurismoComunidad: " + ex.getMessage());
                }
            });

            Button cancelButton = new Button("Cancelar", e -> dialog.close());

            // Agregar campos y botones al layout del diálogo
            formLayout.add(fromComunidadField, fromProvinciaField, toComunidadField, toProvinciaField,
                    fechaInicioField, fechaFinField, periodField, totalField,
                    updateButton, cancelButton);
            dialog.add(formLayout);
            dialog.open();
        });

        // Configurar el Grid para Turismo
        turismoGrid.addColumn(TurismoComunidad::getId).setHeader("ID").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getFrom().getComunidad()).setHeader("From Comunidad").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getFrom().getProvincia()).setHeader("From Provincia").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getTo().getComunidad()).setHeader("To Comunidad").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getTo().getProvincia()).setHeader("To Provincia").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getTimeRange().getFechaInicio()).setHeader("Fecha Inicio").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getTimeRange().getFechaFin()).setHeader("Fecha Fin").setSortable(true);
        turismoGrid.addColumn(turismo -> turismo.getTimeRange().getPeriod()).setHeader("Periodo").setSortable(true);
        turismoGrid.addColumn(TurismoComunidad::getTotal).setHeader("Total").setSortable(true);

        // Rellenar el Grid con datos
        turismoList = turismoComunidadService.getAllTurismoComunidad();
        turismoGrid.setItems(turismoList);

        // Expandir el Grid
        turismoGrid.setSizeFull();

        // Crear filtro por fecha
        DatePicker datePicker = new DatePicker("Filtrar por Fecha");
        datePicker.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                String selectedDate = event.getValue().toString();
                List<TurismoComunidad> filteredList = filterByDate(selectedDate);

                if (filteredList.isEmpty()) {
                    Notification.show("No existen datos para la fecha seleccionada", 3000, Notification.Position.MIDDLE);
                    turismoGrid.setItems(List.of());
                } else {
                    turismoGrid.setItems(filteredList);
                }
            } else {
                turismoGrid.setItems(turismoList);
            }
        });

        layout.add(nuevoButton, datePicker, turismoGrid);
        layout.setFlexGrow(1, turismoGrid); // Expandir el Grid dentro del layout
        return layout;
    }

    private VerticalLayout crearComunidadesLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        // Select para comunidades autónomas
        Select<String> comunidadSelect = new Select<>();
        comunidadSelect.setLabel("Seleccionar Comunidad Autónoma");
        comunidadSelect.setEmptySelectionAllowed(true);
        comunidadSelect.setPlaceholder("Selecciona una comunidad...");

        // Obtener los datos agrupados
        Map<String, List<TurismoComunidad>> agrupadasMap = turismoComunidadService.getAllAgrupadas();

        // Cargar comunidades
        List<String> comunidades = agrupadasMap.keySet().stream()
                .sorted()
                .toList();
        comunidadSelect.setItems(comunidades);

        // Configurar el Grid
        agrupadasGrid.addColumn(TurismoComunidad::getId).setHeader("ID").setSortable(true);
        agrupadasGrid.addColumn(turismo -> turismo.getTo().getComunidad()).setHeader("To Comunidad").setSortable(true);
        agrupadasGrid.addColumn(turismo -> turismo.getTo().getProvincia()).setHeader("To Provincia").setSortable(true);
        agrupadasGrid.addColumn(turismo -> turismo.getTimeRange().getFechaInicio()).setHeader("Fecha Inicio").setSortable(true);
        agrupadasGrid.addColumn(turismo -> turismo.getTimeRange().getFechaFin()).setHeader("Fecha Fin").setSortable(true);
        agrupadasGrid.addColumn(TurismoComunidad::getTotal).setHeader("Total").setSortable(true);

        agrupadasGrid.setSizeFull();

        // Listener para el Select
        comunidadSelect.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                List<TurismoComunidad> filteredList = agrupadasMap.get(event.getValue());
                agrupadasGrid.setItems(filteredList != null ? filteredList : List.of());
            } else {
                agrupadasGrid.setItems(List.of());
            }
        });

        layout.add(comunidadSelect, agrupadasGrid);
        layout.setFlexGrow(1, agrupadasGrid); // Expandir el Grid dentro del layout
        return layout;
    }

    // Método para validar el formulario
    private void validateForm(Button saveButton, TextField... fields) {
        boolean isValid = true;
        for (TextField field : fields) {
            if (field.isInvalid() || field.getValue().isEmpty()) {
                isValid = false;
                break;
            }
        }
        saveButton.setEnabled(isValid);
    }

    private List<TurismoComunidad> filterByDate(String selectedDate) {
        return turismoList.stream()
                .filter(turismo -> turismo.getTimeRange().getFechaInicio().compareTo(selectedDate) <= 0 &&
                        turismo.getTimeRange().getFechaFin().compareTo(selectedDate) >= 0)
                .toList();
    }
}

