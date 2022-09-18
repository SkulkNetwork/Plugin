// The main plugin used for the Skulk Network Minecraft server.
// Copyright (C) 2022-present  RGBCube
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package network.skulk.plugin;

import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BasePlugin;

// TODO: Make custom maps and solve "for \(final \w+ \w+ : [\w. \(\)]+ \{\n[ ]+if"
public final class Plugin extends BasePlugin {
    @Override protected void initExtensions() {
        new EntityOverrideExtension(this);
        new HomesExtension(this);
        new MessageOverrideExtension(this);
        new TPAExtension(this);
    }
}
