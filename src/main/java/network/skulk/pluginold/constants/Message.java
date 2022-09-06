package network.skulk.pluginold.constants;

public abstract class Message {
    public static final String ONLY_PLAYERS_CAN_USE_THIS_COMMAND = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>This command can only be used by players.</red>";
    public static final String TELEPORTING_YOU_TO_X = "<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting you to <bold>%s</bold>...</green>";
    public static final String HOME_ALREADY_EXISTS = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You already have a home named <bold>%s</bold></red>";
    public static final String HOME_LIMIT_REACHED = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You have reached the home limit of 16.</red>";
    public static final String HOME_ADDED = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Added a home named <bold>%s</bold> with the coordinates <bold>%.0f</bold>, <bold>%.0f</bold>, <bold>%.0f</bold>.</green>";
    public static final String NO_SUCH_HOME = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have a home named <bold>%s</bold></red>";
    public static final String HOME_DELETED = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Deleted the home named <bold>%s</bold>.</green>";
    public static final String HOMES = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>All homes:</color>";
    public static final String NO_HOMES = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>You don't have any homes.</color>";
    public static final String HOME = "\n<bold><gray>-></gray></bold> <color:#ffae1a>%s (%.0f, %.0f, %.0f)</color>";
}
