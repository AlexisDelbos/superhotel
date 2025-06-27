package fr.fms.dto;

public record RecommandationDto(
        String name,
        String cityName,
        int occupancyRate,
        String recommendation
) {
}
