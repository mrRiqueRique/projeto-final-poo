#!/bin/sh
exec java --module-path $SNAP/lib --add-modules javafx.controls,javafx.fxml -jar $SNAP/bin/projetofinal-1.0.0.jar
