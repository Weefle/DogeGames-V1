package net.dogegames.dogebungee.player;

import net.dogegames.dogebungee.helper.DateHelper;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

@Embedded
public class DogeBungeePlayerSanction {
    @Property("type")
    private SanctionType type;
    @Reference(value = "moderator", idOnly = true)
    private DogeBungeePlayer moderator;
    @Property("reason")
    private String reason;
    @Property("when")
    private Date when;
    @Property("finish")
    private Date finish;
    @Reference(value = "canceller", idOnly = true)
    private DogeBungeePlayer canceller;
    @Property("cancel_reason")
    private String cancelReason;

    /**
     * Add sanction to a {@link DogeBungeePlayer}.
     *
     * @param type      Sanction's type.
     * @param moderator The moderator ({@link DogeBungeePlayer}).
     * @param reason    Reason.
     * @param finish    Finish when.
     */
    public DogeBungeePlayerSanction(SanctionType type, DogeBungeePlayer moderator, String reason, Date finish) {
        this.type = type;
        this.moderator = moderator;
        this.reason = reason;
        this.when = new Date();
        this.finish = finish;
    }

    private DogeBungeePlayerSanction() {
    }

    /**
     * @return Sanction's type.
     */
    public SanctionType getType() {
        return type;
    }

    /**
     * @return The moderator ({@link DogeBungeePlayer}).
     */
    public DogeBungeePlayer getModerator() {
        return moderator;
    }

    /**
     * @return Reason.
     */
    public String getReason() {
        return reason;
    }

    /**
     * @return When.
     */
    public Date getWhen() {
        return when;
    }

    /**
     * @return Finish when.
     */
    public Date getFinish() {
        return finish;
    }

    /**
     * @return The canceller ({@link DogeBungeePlayer}).
     */
    public DogeBungeePlayer getCanceller() {
        return canceller;
    }

    /**
     * @return The cancel reason (if there is one xD).
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * Cancel a sanction.
     *
     * @param canceller The canceller.
     * @param reason    Reason.
     */
    public void cancel(DogeBungeePlayer canceller, String reason) {
        this.canceller = canceller;
        this.reason = reason;
    }

    /**
     * @return The sanction message;
     */
    public String getMessage() {
        String message = "§cVous avez été " + type.getWord() + " ";
        message += tlFinish();
        message += " par " + moderator.getUsername() + ".\n§cRaison : " + (reason == null ? "non spécifié." : reason);
        return message;
    }

    /**
     * @return Translate finish date.
     */
    public String tlFinish() {
        return finish == null ? "à vie" : "jusqu'au " + DateHelper.UNTIL_FORMAT.format(finish);
    }

    /**
     * Contains all {@link SanctionType}.
     */
    public enum SanctionType {
        MUTE("mute"),
        BAN("banni");

        private String word;

        SanctionType(String word) {
            this.word = word;
        }

        /**
         * @return The word translated in french.
         */
        public String getWord() {
            return word;
        }
    }
}
