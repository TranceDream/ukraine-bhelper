import { Country, State, City } from 'country-state-city'

export interface CountryModel {
    code: string
    country: string
}

export interface StateModel {
    code: string
    state: string
}

export interface CityModel {
    city: string
}

export const getCountries = (): CountryModel[] => {
    return Country.getAllCountries().map((country) => ({
        code: country.isoCode,
        country: country.name,
    }))
}

export const getStates = (code: string): StateModel[] => {
    return State.getStatesOfCountry(code).map((state) => ({
        code: state.isoCode,
        state: state.name,
    }))
}

export const getCities = (
    countryCode: string,
    stateCode: string
): CityModel[] => {
    return City.getCitiesOfState(countryCode, stateCode).map((city) => ({
        city: city.name,
    }))
}
