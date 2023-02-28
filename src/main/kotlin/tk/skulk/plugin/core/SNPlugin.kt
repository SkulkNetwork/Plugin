package tk.skulk.plugin.core

import tk.skulk.plugin.framework.Extension
import tk.skulk.plugin.framework.Plugin

class SNPlugin : Plugin() {
    override val extensions = listOf<() -> Extension<SNPlugin>>(

    )
}
