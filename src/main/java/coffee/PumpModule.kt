package coffee

import dagger.Module
import dagger.Provides

@Module
class PumpModule {
    @Provides fun providePump(pump: Thermosiphon): Pump {
        return pump
    }
}