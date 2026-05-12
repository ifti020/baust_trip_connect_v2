// 🚨 DUMMY SERVICE FILE — FIX LATER WITH REAL BACKEND ENDPOINTS
// Used in HelpCenter.vue & GuideDetails.vue

export const getFaqs = async () => {
    return [{ question: 'Dummy FAQ?', answer: 'This is placeholder.' }]
}

export const getGuides = async () => {
    return [{ id: 1, title: 'Dummy Guide', content: 'Placeholder content.' }]
}

export const getGuideById = async (id) => {
    return { id, title: 'Dummy Guide', content: 'Placeholder content.' }
}
