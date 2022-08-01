package network.skulk.plugin.constants;

public abstract class Message {
    public static final String ONLY_PLAYERS_CAN_USE_THIS_COMMAND = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>This command can only be used by players.</red>";
    public static final String PLAYER_NOT_ONLINE = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>This player is not online.</red>";

    /* TPA Commands */

    // Expiring.
    public static final String TPA_REQUEST_TO_X_EXPIRED = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>Your TPA request to <bold>%s</bold> has expired.</color>";
    public static final String TPA_REQUEST_X_SENT_TO_YOU_EXPIRED = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>The TPA request <bold>%s</bold> has sent to you has expired.</color>";

    // Sent.
    public static final String TPA_REQUEST_SENT_TO_X = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Sent a TPA request to <bold>%s</bold>.</green>";
    public static final String TPA_REQUEST_SENT_BY_X = "<bold><gray>[ <blue>?</blue> ]</gray> %s</bold> has sent a TPA request to you. Do you accept? <bold><green><click:run_command:/tpa-accept %s>[✓]</click></green> <red><click:run_command:/tpa-reject %s>[✗]</click></red></bold>";

    // Already exists.
    public static final String TPA_REQUEST_TO_X_ALREADY_EXISTS = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray></bold> <color:#ffae1a>You already have a pending TPA request to <bold>%s</bold>.</color>";

    // Teleporting.
    public static final String TELEPORTING_YOU_TO_X = "<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting you to <bold>%s</bold>...</green>";
    public static final String TELEPORTING_X_TO_YOU = "<bold><gray>[ <green>!</green> ]</gray></bold> <green>Teleporting <bold>%s</bold> to you...</green>";

    // No requests.
    public static final String NO_OUTGOING_TPA_REQUEST_TO_X = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have an outgoing TPA request to <bold>%s</bold>.</red>";
    public static final String NO_INCOMING_TPA_REQUESTS = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You don't have any incoming TPA requests.</red>";
    public static final String X_DOESNT_WANT_TO_TPA_TO_YOU = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray> <color:#ffae1a>%s</bold> doesn't want to TPA to you.</color>";

    // You are ignored.
    public static final String X_IGNORING_YOU = "<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from you.</red>";
    public static final String X_IGNORING_ALL = "<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> doesn't accept TPA requests from anyone.</red>";

    // TPA-ACCEPT
    public static final String TPA_ACCEPT_DIALOG = "<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple people wanting to TPA to you. Which one would you like to accept?</blue>";
    public static final String TPA_ACCEPT_DIALOG_OPTION = "\n<bold><gray>-></gray> <green><click:run_command:/tpa-accept %s>[%s]</click></green></bold>";

    // TPA-REJECT
    public static final String TPA_REQUEST_FROM_X_REJECTED = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Rejected the TPA request from <bold>%s</bold>.</green>";
    public static final String X_HAS_REJECTED_YOUR_TPA_REQUEST = "<bold><gray>[ <red>!</red> ]</gray> <red>%s</bold> has rejected your TPA request.</red>";
    public static final String TPA_REJECT_DIALOG = "<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple people wanting to TPA to you. Which one would you like to reject?</blue>";
    public static final String TPA_REJECT_DIALOG_OPTION = "\n<bold><gray>-></gray> <red><click:run_command:/tpa-reject %s>[%s]</click></red></bold>";

    // TPA-CANCEL
    public static final String TPA_REQUEST_FROM_X_CANCELLED = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Cancelled the TPA request going to <bold>%s</bold>.</green>";
    public static final String TPA_REQUEST_FROM_X_CANCELLED_BY_SENDER = "<bold><gray>[ <color:#ffae1a>!</color:#ffae1a> ]</gray> <color:#ffae1a>%s</bold> has cancelled their TPA request to you.</color>";
    public static final String TPA_CANCEL_DIALOG = "<bold><gray>[ <blue>?</blue> ]</gray></bold> <blue>Seems like you have multiple incoming TPA requests. Which one would you like to cancel?</blue>";
    public static final String TPA_CANCEL_DIALOG_OPTION = "\n<bold><gray>-></gray> <color:#ffae1a><click:run_command:/tpa-cancel %s>[%s]</click></color></bold>";

    // TPA-IGNORE
    public static final String TPA_IGNORED_X = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Ignoring the TPA requests from %s indefinitely.</green>";
    public static final String UN_TPA_IGNORED_X = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Un-Ignored TPA requests from %s.</green>";

    // TPA-IGNORE-ALL
    public static final String TPA_IGNORED_ALL = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Ignoring all TPA requests.</green>";
    public static final String UN_TPA_IGNORED_ALL = "<bold><gray>[ <green>✓</green> ]</gray></bold> <green>Un-Ignored all TPA requests.</green>";
    public static final String ALREADY_TPA_IGNORING_ALL = "<bold><gray>[ <red>!</red> ]</gray></bold> <red>You are already ignoring all TPA requests.</red>";

    // TPA-LIST-IGNORED
    public static final String TPA_IGNORING_ALL = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray><bold> <color:#ffae1a>You are ignoring <bold>all</bold> TPA requests.</color>";
    public static final String TPA_IGNORING_FOLLOWING = "<bold><gray>[ <color:#ffae1a>!</color> ]</gray><bold> <color:#ffae1a>You are ignoring the TPA requests from the following people:</color>";
    public static final String TPA_IGNORING_FOLLOWING_ITEM = "\n<bold><gray>-> %s</gray>";
}
