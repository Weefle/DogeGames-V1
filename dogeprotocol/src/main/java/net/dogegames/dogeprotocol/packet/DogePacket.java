package net.dogegames.dogeprotocol.packet;

import net.dogegames.dogeprotocol.DogePacketHandler;

import java.io.DataInput;
import java.io.DataOutput;

public interface DogePacket {
    /**
     * Read the packet from a {@link DataInput}.
     *
     * @param input The input.
     * @throws Exception If there an exception.
     */
    void read(DataInput input) throws Exception;

    /**
     * Write the packet on a {@link DataOutput}.
     *
     * @param output The output.
     * @throws Exception If there an exception.
     */
    void write(DataOutput output) throws Exception;

    /**
     * Handling the packet on {@link DogePacketHandler}.
     *
     * @param handler The handler.
     */
    void handle(DogePacketHandler handler);
}
