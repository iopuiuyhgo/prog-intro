package game;

/**
 * @author Konstantin Tsitsin (iopuiuyhgo@gmail.com)
 */
public interface Player {
    Move move(Position position, Cell cell);
}
