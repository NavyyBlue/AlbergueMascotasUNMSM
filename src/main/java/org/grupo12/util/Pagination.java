package org.grupo12.util;
import lombok.Data;

@Data
public class Pagination {
    private int offset = 0;
    private int limit = 9;
    private int total;
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;

    public void calculate() {
        // Calcular el número de páginas totales
        totalPages = (int) Math.ceil((double) total / limit);

        // Asegurar que currentPage esté dentro de los límites
        currentPage = Math.min(Math.max(1, currentPage), totalPages);

        // Calcular el índice de inicio y fin de las páginas a mostrar
        int maxPagesToShow = 5;
        startPage = Math.max(1, currentPage - maxPagesToShow / 2);
        endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

        // Ajustar el índice de inicio si es necesario
        if (endPage - startPage + 1 < maxPagesToShow) {
            startPage = Math.max(1, endPage - maxPagesToShow + 1);
        }
    }
}
