// custom typefaces
import "typeface-montserrat"
import "typeface-merriweather"

const preferDefault = m => (m && m.default) || m;

export const wrapRootElement = preferDefault(require(`./wrap-with-provider`));
