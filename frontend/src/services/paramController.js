export default function paramController() {
    const params = JSON.parse(localStorage.getItem('id'));

    if (params) {
        return { id:  params.id };
    } else {
        return {};
    }
}