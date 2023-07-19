package com.example.crm.excel;

public class StylesGenerator {
    fun prepareStyles(wb: Workbook): Map<CustomCellStyle, CellStyle> {
        val boldArial = createBoldArialFont(wb)
        val redBoldArial = createRedBoldArialFont(wb)

        val rightAlignedStyle = createRightAlignedStyle(wb)
        val greyCenteredBoldArialWithBorderStyle =
            createGreyCenteredBoldArialWithBorderStyle(wb, boldArial)
        val redBoldArialWithBorderStyle =
            createRedBoldArialWithBorderStyle(wb, redBoldArial)
        val rightAlignedDateFormatStyle =
            createRightAlignedDateFormatStyle(wb)

        return

    mapOf(
            CustomCellStyle.RIGHT_ALIGNED to rightAlignedStyle,
            CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER to greyCenteredBoldArialWithBorderStyle,
            CustomCellStyle.RED_BOLD_ARIAL_WITH_BORDER to redBoldArialWithBorderStyle,
            CustomCellStyle.RIGHT_ALIGNED_DATE_FORMAT to rightAlignedDateFormatStyle
        )
    }
}
