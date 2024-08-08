package me.ks.chan.material.symbols.ksp.repository

sealed class PathBuilderCommand(
    val command: String,
    val floatParameters: FloatArray
) {
    class LineTo(floatParameterList: List<Float>): PathBuilderCommand(
        command = "lineTo", floatParameters = floatParameterList.toFloatArray()
    )
    class MoveTo(floatParameterList: List<Float>): PathBuilderCommand(
        command = "moveTo", floatParameters = floatParameterList.toFloatArray()
    )
    class QuadTo(floatParameterList: List<Float>): PathBuilderCommand(
        command = "quadTo", floatParameters = floatParameterList.toFloatArray()
    )
    data object Close : PathBuilderCommand(
        command = "close", floatParameters = floatArrayOf()
    )
}

object PathBuilderRepository: ProcessRepository<List<String>, List<PathBuilderCommand>> {

    private enum class VectorDrawablePathCommand(val command: Char) {
        MoveTo('M'),
        LineTo('L'),
        QuadTo('Q'),
        Close('Z'),;

        companion object {
            operator fun get(command: Char) = VectorDrawablePathCommand.entries
                .find { it.command == command }
        }

        fun pathBuilderCommand(parameters: String): PathBuilderCommand {
            val floatParameters = parameters.substring(1)
                .split(',', ' ')
                .filter(String::isNotBlank)
                .map(String::toFloat)

            return when (this) {
                MoveTo -> PathBuilderCommand.MoveTo(floatParameters)
                LineTo -> PathBuilderCommand.LineTo(floatParameters)
                QuadTo -> PathBuilderCommand.QuadTo(floatParameters)
                Close -> PathBuilderCommand.Close
            }
        }
    }

    override fun process(unprocessed: List<String>): List<PathBuilderCommand> {
        return unprocessed.mapNotNull { pathCommand ->
            VectorDrawablePathCommand[pathCommand.first()]?.pathBuilderCommand(pathCommand)
        }
    }

}