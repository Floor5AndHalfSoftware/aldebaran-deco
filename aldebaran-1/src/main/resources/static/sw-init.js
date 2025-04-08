// Register custom sw
if ('serviceWorker' in navigator) {
    navigator
        .serviceWorker
        .register('sw-aldebaran.js')
        .then(function (registration) {
            console.log('[sw] service worker registered - scope: ', registration.scope);
        }, function (err) {
            // registration failed :(
            console.log('[sw] service worker registration failed: ', err);
        });
}