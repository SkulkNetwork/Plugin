package network.skulk.plugin.constants;

public abstract class Message {
    public static final String CONSOLE_NOT_ALLOWED = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>This command can only be used by players.</red>";
    public static final String PLAYER_OFFLINE = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>This player is not online.</red>";

    // TPA

    // Expiring.
    public static final String TPA_REQUEST_TO_X_EXPIRED = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>Your TPA request to <bold>%s</bold> has expired.</color>";
    public static final String TPA_REQUEST_X_SENT_TO_YOU_EXPIRED = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>The TPA request <bold>%s</bold> has sent to you has expired.</color>";

    // Sent.
    public static final String TPA_REQUEST_SENT_TO_X = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Sent a TPA request to <bold>%s</bold>.</green>";
    public static final String TPA_REQUEST_SENT_BY_X_RECEIVED = "<bold><gray>[ <blue>?</blue> ]</gray> %s</bold> has sent a TPA request to you. Do you accept? <bold><green><click:run_command:/tpa-accept %s>[✓]</click></green> <red><click:run_command:/tpa-reject %s>[✗]</click></red></bold>";

    // Already exists.
    public static final String TPA_REQUEST_TO_X_ALREADY_EXISTS = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>You already have a pending TPA request to <bold>%s</bold>.</color>";

    // Teleporting you...
    public static final String TELEPORTING_X_TO_YOU = "<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting <bold>%s</bold> to you...</green>";
    public static final String TELEPORTING_YOU_TO_X = "<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting you to <bold>%s</bold>...</green>";

    // No requests.
    public static final String NO_OUTGOING_TPA_REQUESTS = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have an outgoing TPA request to <bold>%s</bold>.</red>";
    public static final String NO_INCOMING_TPA_REQUESTS = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have any incoming TPA requests.</red>";
    public static final String X_DOESNT_WANT_TO_TPA_TO_YOU = "<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't want to TPA to you.</red>";

    // TPA-ACCEPT
    public static final String TPA_ACCEPT_DIALOG = "<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple people wanting to TPA to you. Which one would you like to accept?</blue>";
    public static final String TPA_ACCEPT_DIALOG_OPTION = "\n<bold><green><click:run_command:/tpa-accept %s>[%s]</click></green></bold>";

    // TPA-REJECT
    public static final String TPA_REQUEST_FROM_X_REJECTED = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Rejected <bold>%s</bold>'s TPA request.</green>";
    public static final String X_HAS_REJECTED_YOUR_TPA_REQUEST = "<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> has rejected your TPA request.</red>";
    public static final String TPA_REJECT_DIALOG = "<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple people wanting to TPA to you. Which one would you like to reject?</blue>";
    public static final String TPA_REJECT_DIALOG_OPTION = "\n<bold><red><click:run_command:/tpa-reject %s>[%s]</click></red></bold>";

    // TPA-CANCEL
    public static final String TPA_CANCEL_DIALOG = "<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple incoming TPA requests. Which one would you like to cancel?</blue>";
    public static final String TPA_CANCEL_DIALOG_OPTION = "\n<bold><gray>-></grey> <color:#ffae1a><click:run_command:/tpa-cancel %s>[%s]</click></color></bold>";
    public static final String TPA_REQUEST_FROM_X_CANCELLED_BY_SENDER = "<bold><gray>[ <color:#ffae1a>!</color:#ffae1a> ]</gray> <color:#ffae1a>%s</bold> has cancelled their TPA request to you.</color>";
}
